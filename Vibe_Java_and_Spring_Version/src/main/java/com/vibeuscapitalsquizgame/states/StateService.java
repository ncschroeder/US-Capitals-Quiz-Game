package com.vibeuscapitalsquizgame.states;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.*;

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

    List<State> parseFile(MultipartFile file) throws IOException {
        List<String> lines;
        try (var reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            lines = reader.lines().filter(line -> !line.isBlank()).toList();
        }

        if (lines.isEmpty()) {
            throw new InvalidStateFileException("File is empty");
        }

        String firstLine = lines.getFirst();
        int expectedCount = firstLine.split(",").length;
        if (expectedCount < 3) {
            throw new InvalidStateFileException(
                "First line must have at least 3 values: " + firstLine
            );
        }

        List<State> parsed =
            lines
            .stream()
            .map(line -> parseLine(line, expectedCount))
            .toList();

        var seen = new HashSet<String>();
        for (State state : parsed) {
            if (!seen.add(state.getName())) {
                throw new InvalidStateFileException(
                    "Duplicate state name: " + state.getName()
                );
            }
        }

        return parsed;
    }

    private State parseLine(String line, int expectedCount) {
        String[] parts = line.split(",");
        if (parts.length != expectedCount) {
            throw new InvalidStateFileException(
                String.format("Expected %d values but got %d: %s", expectedCount, parts.length, line)
            );
        }

        if (Arrays.stream(parts).anyMatch(String::isBlank)) {
            throw new InvalidStateFileException("Blank value in line: " + line);
        }

        String name = parts[0].trim();
        String capital = parts[1].trim();
        List<String> cities =
            Arrays.stream(parts, 2, parts.length)
            .map(String::trim)
            .toList();

        var seen = new HashSet<String>();
        for (String city : cities) {
            if (!seen.add(city)) {
                throw new InvalidStateFileException(
                    String.format("Duplicate city in %s: %s", name, city)
                );
            }
        }

        return new State(name, capital, cities);
    }
}
