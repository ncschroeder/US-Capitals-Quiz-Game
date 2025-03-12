package uscapitalsquizgame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.*;
import java.util.stream.*;
import uscapitalsquizgame.statetypes.*;

@Service
public class AdminService {
    @Autowired
    private StatesRepository statesRepository;
    
    public List<EntityState> getStates(MultipartFile file) {
        try (
            var isr = new InputStreamReader(file.getInputStream());
            var br = new BufferedReader(isr)
        ) {
            String line1 = br.readLine();
            var numElements =
                (int)
                line1
                .chars()
                .filter(c -> c == ',')
                .count()
                + 1;
            
            List<EntityState> states =
                Stream.concat(Stream.of(line1), br.lines())
                .map(line -> createState(line, numElements))
                .sorted(Comparator.comparing(EntityState::getName))
                .toList();
            
            if (states.size() != 50) {
                throw new RuntimeException(String.format("Found %d states.", states.size()));
            }
            return states;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private EntityState createState(String line, int numElements) {
        List<String> lineValues =
            Arrays.stream(line.split(","))
            .map(String::trim)
            .toList();
        
        if (lineValues.size() != numElements) {
            throw new RuntimeException(
                String.format("Wrong number of values for this line: %s.", line)
            );
        }
        
        String
            stateName = lineValues.get(0),
            capital = lineValues.get(1),
            cities =
                lineValues
                .stream()
                .skip(2)
                .sorted()
                .collect(Collectors.joining(","));
        
        return new EntityState(stateName, capital, cities);
    }
    
    public List<String> getStatesDataTableRowsHtml() {
        return
            statesRepository
            .findAll()
            .flatMap(state -> {
                var citiesString = state.getCities().replace(",", ", ");
                return """
                    <tr>
                        <td>%s</td>
                        <td>%s</td>
                        <td>%s</td>
                    </tr>"""
                    .formatted(state.getName(), state.getCapital(), citiesString)
                    .lines();
            })
            .toList();
    }
}
