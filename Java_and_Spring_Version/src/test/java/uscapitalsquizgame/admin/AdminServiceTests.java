package uscapitalsquizgame.admin;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.mock.web.MockMultipartFile;
import uscapitalsquizgame.states.State;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTests {
    @InjectMocks
    AdminService adminService;
    
    List<State> createStates(Path csvPath) {
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(csvPath);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return adminService.createStates(new MockMultipartFile(csvPath.toString(), bytes));
    }
    
    static List<Path> getCsvPaths(String dirName) {
        var dir = Path.of("src", "test", "resources", dirName);
        try (Stream<Path> paths = Files.list(dir)) {
            return paths.toList();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @ParameterizedTest
    @FieldSource("validCsvPaths")
    void createStatesReturnValue(Path csvPath) {
        List<State> expectedStates =
            List.of(
                new State("Alaska", "Juneau", "Anchorage,Dillingham,Fairbanks,North Pole,Sitka")
            );
        assertEquals(expectedStates, createStates(csvPath));
    }
    
    static final List<Path> validCsvPaths = getCsvPaths("valid_csvs");
    
    @ParameterizedTest
    @FieldSource("invalidCsvPaths")
    void createStatesThrows(Path csvPath) {
        assertThrows(CsvException.class, () -> createStates(csvPath));
    }
    
    static final List<Path> invalidCsvPaths = getCsvPaths("invalid_csvs");
}
