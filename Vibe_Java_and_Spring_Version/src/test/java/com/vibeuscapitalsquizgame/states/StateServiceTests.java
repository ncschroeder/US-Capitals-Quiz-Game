package com.vibeuscapitalsquizgame.states;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class StateServiceTests {
    private final StateService stateService = new StateService(null);
    
    private MultipartFile file(String contents) {
        return new MockMultipartFile("file", contents.getBytes());
    }
    

    @ParameterizedTest
    @MethodSource("validFiles")
    void parsesValidFile(String contents, List<State> expected) throws IOException {
        MultipartFile file = file(contents);
        assertEquals(expected, stateService.parseFile(file));
    }

    static Stream<Arguments> validFiles() {
        return Stream.of(
            arguments(
                "Alabama,Montgomery,Birmingham,Huntsville",
                List.of(new State("Alabama", "Montgomery", List.of("Birmingham", "Huntsville")))
            ),
            arguments(
                """
                Alabama,Montgomery,Birmingham,Huntsville
                Alaska,Juneau,Anchorage,Fairbanks
                """,
                List.of(
                    new State("Alabama", "Montgomery", List.of("Birmingham", "Huntsville")),
                    new State("Alaska", "Juneau", List.of("Anchorage", "Fairbanks"))
                )
            ),
            arguments(
                "  Alabama  ,  Montgomery  ,  Birmingham  ",
                List.of(new State("Alabama", "Montgomery", List.of("Birmingham")))
            ),
            arguments(
                """

                Alabama,Montgomery,Birmingham

                Alaska,Juneau,Anchorage
                """,
                List.of(
                    new State("Alabama", "Montgomery", List.of("Birmingham")),
                    new State("Alaska", "Juneau", List.of("Anchorage"))
                )
            )
        );
    }

    
    @ParameterizedTest
    @ValueSource(strings = {
        "",
        "\n\n   \n",
        "Alabama,Montgomery",
        """
        Alabama,Montgomery,Birmingham
        Alaska,Juneau,Anchorage,Fairbanks
        """,
        """
        Alabama,Montgomery,Birmingham
        Alabama,Huntsville,Mobile
        """,
        "Alabama,Montgomery,Birmingham,Birmingham",
        "Alabama, ,Birmingham"
    })
    void rejectsInvalidFile(String contents) {
        MultipartFile file = file(contents);
        assertThrows(InvalidStateFileException.class, () -> stateService.parseFile(file));
    }
}