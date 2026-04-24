package com.vibeuscapitalsquizgame.states;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@Service
public class StateService {
    private final StateRepository stateRepository;
    private List<State> states = List.of();

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadStates() {
        states = List.copyOf(stateRepository.findAll());
    }

    public List<State> getStates() {
        return states;
    }

    public void uploadStates(MultipartFile file) throws IOException {
        List<State> parsed = parseFile(file);
        stateRepository.deleteAll();
        stateRepository.saveAll(parsed);
        loadStates();
    }

    private List<State> parseFile(MultipartFile file) throws IOException {
        List<String> lines;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            lines = reader.lines().filter(line -> !line.isBlank()).toList();
        }

        if (lines.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty");
        }

        String firstLine = lines.getFirst();
        int expectedCount = firstLine.split(",").length;
        if (expectedCount < 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "First line must have at least 3 values: " + firstLine);
        }

        return lines.stream()
            .map(line -> parseLine(line, expectedCount))
            .toList();
    }

    private State parseLine(String line, int expectedCount) {
        String[] parts = line.split(",");
        if (parts.length != expectedCount) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                String.format("Expected %d values but got %d: %s", expectedCount, parts.length, line));
        }
        String name = parts[0].trim();
        String capital = parts[1].trim();
        List<String> cities = Arrays.stream(parts, 2, parts.length)
            .map(String::trim)
            .toList();
        return new State(name, capital, cities);
    }
}
