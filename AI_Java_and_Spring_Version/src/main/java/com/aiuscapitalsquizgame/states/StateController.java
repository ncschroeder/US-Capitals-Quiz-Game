package com.aiuscapitalsquizgame.states;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
public class StateController {
    @Autowired
    private StateService stateService;

    @PostMapping("/states/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        stateService.uploadStates(file);
        return String.format(
            "Upload successful. There are now %d states in the database.",
            stateService.getStates().size()
        );
    }

    @ExceptionHandler(InvalidStateFileException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidStateFile(InvalidStateFileException exception) {
        return exception.getMessage();
    }
}