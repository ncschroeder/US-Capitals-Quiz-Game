package com.uscapitalsquizgame.admin;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import com.uscapitalsquizgame.states.State;

import static org.junit.jupiter.api.Assertions.*;

public class AdminServiceTests {
    AdminService adminService = new AdminService();
    
    @ParameterizedTest
    @FieldSource("validCsvFiles")
    void createStatesReturnValue(MultipartFile file) {
        var expectedState =
            new State(
                "Alaska",
                "Juneau",
                List.of("Anchorage", "Dillingham", "Fairbanks", "North Pole", "Sitka")
            );
        
        assertEquals(List.of(expectedState), adminService.createStates(file));
    }
    
    static final List<MockMultipartFile> validCsvFiles = getFiles("valid_csvs");
    
    @ParameterizedTest
    @FieldSource("invalidCsvFiles")
    void createStatesThrows(MultipartFile file) {
        assertThrows(CsvException.class, () -> adminService.createStates(file));
    }
    
    static final List<MockMultipartFile> invalidCsvFiles = getFiles("invalid_csvs");
    
    static List<MockMultipartFile> getFiles(String dirName) {
        var dirPath = Path.of("src", "test", "resources", dirName);
        try (Stream<Path> paths = Files.list(dirPath)) {
            return
                paths
                .map(path -> {
                    byte[] bytes;
                    try {
                        bytes = Files.readAllBytes(path);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    var mockFileName = path.toString();
                    return new MockMultipartFile(mockFileName, bytes);
                })
                .toList();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
