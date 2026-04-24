package com.vibeuscapitalsquizgame.states;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class StateController {
    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @PostMapping("/states/upload")
    public void upload(@RequestParam("file") MultipartFile file) throws IOException {
        stateService.uploadStates(file);
    }
}