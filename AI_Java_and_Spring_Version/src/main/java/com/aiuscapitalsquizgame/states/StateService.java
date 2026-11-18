package com.aiuscapitalsquizgame.states;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.*;

@Service
public class StateService {
    @Autowired
    private StateRepository stateRepository;
    
    private List<State> states = List.of();

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

    /**
     * Parses an uploaded CSV file into a list of states.
     *
     * Each non-blank line has the form {state,capital,city1,city2,...}. Blank
     * lines are skipped and every value is trimmed. The first line sets how many values
     * each line must have, and it must have at least 4 (a state, a capital and at least
     * two cities).
     *
     * Each state's cities are sorted alphabetically, and the returned states are
     * sorted by name.
     *
     * Throws an InvalidStateFileException in various situations and throws an IOException
     * if the file cannot be read
     */
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
        if (expectedCount < 4) {
            throw new InvalidStateFileException(
                "First line must have at least 4 values: " + firstLine
            );
        }

        List<State> parsed =
            lines
            .stream()
            .map(line -> parseLine(line, expectedCount))
            .sorted(Comparator.comparing(State::getName))
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

    /**
     * Parses one line of the CSV file into a state.
     *
     * The line has the form {state,capital,city1,city2,...} and must have exactly
     * expectedCount values. Every value is trimmed, and the cities are sorted
     * alphabetically.
     *
     * Throws an InvalidStateFileException in various situations.
     */
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
            .sorted()
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
