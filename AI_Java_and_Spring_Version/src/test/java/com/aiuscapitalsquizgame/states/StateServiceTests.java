package com.aiuscapitalsquizgame.states;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class StateServiceTests {
    private final StateService stateService = new StateService();
    
    private MultipartFile file(String contents) {
        return new MockMultipartFile("file", contents.getBytes());
    }

    @ParameterizedTest
    @FieldSource("validFiles")
    void parsesValidFile(String contents, List<State> expected) throws IOException {
        assertEquals(expected, stateService.parseFile(file(contents)));
    }

    static final List<Arguments> validFiles =
        List.of(
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
        assertThrows(InvalidStateFileException.class, () -> stateService.parseFile(file(contents)));
    }
}