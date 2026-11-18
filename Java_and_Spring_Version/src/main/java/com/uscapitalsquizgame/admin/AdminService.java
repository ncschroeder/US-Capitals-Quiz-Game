package com.uscapitalsquizgame.admin;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.*;
import com.uscapitalsquizgame.states.State;

@Service
public class AdminService {
    /**
     * The file should have lines with comma-separated values. Each line should have a state, capital,
     * and non-capital cities. This method reads the file and returns a list of States sorted
     * alphabetically by state names. The cities for each State will also be sorted alphabetically.
     */
    List<State> createStates(MultipartFile file) {
        var states = new ArrayList<State>();
        
        try (var br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line1 = br.readLine();
            List<String> line1Values = getValues(line1);
            if (line1Values.size() < 4) {
                throw new CsvException(String.format("Line 1 (\"%s\") has < 4 values.", line1));
            }
            
            states.add(createState(line1, line1Values));
            var stateNames = new HashSet<String>();
            stateNames.add(states.getFirst().getName());
            
            String curLine;
            while ((curLine = br.readLine()) != null) {
                List<String> curLineValues = getValues(curLine);
                if (curLineValues.size() != line1Values.size()) {
                    String exceptionMessageEnd =
                        String.format("doesn't have the same number of values as line 1 (\"%s\")", line1);
                    throw new CsvException(curLine, exceptionMessageEnd);
                }
                
                var state = createState(curLine, curLineValues);
                if (!stateNames.add(state.getName())) {
                    throw new CsvException(String.format("Duplicate state name: %s.", state.getName()));
                }
                states.add(state);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        states.sort(Comparator.comparing(State::getName));
        return states;
    }
    
    private List<String> getValues(String csvLine) {
        return
            Arrays.stream(csvLine.split(","))
            .map(value -> {
                if (value.isBlank()) {
                    throw new CsvException(csvLine, "has blank value");
                }
                return value.strip().replaceAll("\\s+", " ");
            })
            .toList();
    }
    
    private State createState(String csvLine, List<String> csvLineValues) {
        String stateName = csvLineValues.get(0);
        String capital = csvLineValues.get(1);
        
        List<String> cities = csvLineValues.subList(2, csvLineValues.size());
        if (cities.stream().distinct().count() != cities.size()) {
            throw new CsvException(csvLine, "has duplicate city");
        }
        cities = cities.stream().sorted().toList();
        
        return new State(stateName, capital, cities);
    }
    }
}