package uscapitalsquizgame.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import uscapitalsquizgame.statetypes.EntityState;
import uscapitalsquizgame.statetypes.StatesRepository;

@Service
public class AdminService {
    @Autowired
    private StatesRepository statesRepository;
    
    List<EntityState> createStates(MultipartFile file) {
        var states = new ArrayList<EntityState>();
        
        try (
            var isr = new InputStreamReader(file.getInputStream());
            var br = new BufferedReader(isr)
        ) {
            String line1 = br.readLine();
            List<String> line1Values = getValues(line1);
            if (line1Values.size() < 3) {
                throw new CsvException(String.format("Line 1 (\"%s\") has < 3 values.", line1));
            }
            
            states.add(createState(line1, line1Values));
            var stateNames = new HashSet<String>();
            stateNames.add(states.getFirst().getName());
            
            String curLine;
            while ((curLine = br.readLine()) != null) {
                List<String> curLineValues = getValues(curLine);
                if (curLineValues.size() != line1Values.size()) {
                    String exceptionMessageEnd =
                        String.format(
                            "doesn't have the same number of values as line 1 (\"%s\")", line1
                        );
                    throw new CsvException(curLine, exceptionMessageEnd);
                }
                
                EntityState state = createState(curLine, curLineValues);
                if (!stateNames.add(state.getName())) {
                    throw new CsvException(
                        String.format("Duplicate state name: %s.", state.getName())
                    );
                }
                states.add(state);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        states.sort(Comparator.comparing(EntityState::getName));
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
    
    private EntityState createState(String csvLine, List<String> csvLineValues) {
        String stateName = csvLineValues.get(0);
        String capital = csvLineValues.get(1);
        
        List<String> citiesList = csvLineValues.subList(2, csvLineValues.size());
        if (citiesList.stream().distinct().count() != citiesList.size()) {
            throw new CsvException(csvLine, "has duplicate city");
        }
        String citiesString =
            citiesList
            .stream()
            .sorted()
            .collect(Collectors.joining(","));
        
        return new EntityState(stateName, capital, citiesString);
    }
    
    
    List<String> getStatesTableRowsHtml() {
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
