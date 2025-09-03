package uscapitalsquizgame.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;
import uscapitalsquizgame.RandomnessService;
import uscapitalsquizgame.scoredatatypes.ScoreData;
import uscapitalsquizgame.scoredatatypes.ScoreDataRepository;
import uscapitalsquizgame.statetypes.*;

@RestController
@RequestMapping("/admin")
public class AdminRestController {
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private StatesRepository statesRepository;
    
    @Autowired
    private RandomnessService randomnessService;
    
    @Autowired
    private ScoreDataRepository scoreDataRepository;
    
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    private String getResponseBody(CsvException ex) {
        return ex.getMessage();
    }
    
    @PutMapping("/states")
    public String updateStates(@RequestParam MultipartFile file) {
        // Call createStates first since it might throw a CsvException.
        List<EntityState> states = adminService.createStates(file);
        statesRepository.deleteAll();
        statesRepository.saveAll(states);
        randomnessService.updateStates();
        return String.format(
            "Update successful. There are now %d states in the database.", states.size()
        );
    }
    
    
    @GetMapping("/statesFromDatabase")
    public List<NonEntityState> getStatesFromDatabase() {
        return
            statesRepository
            .findAll()
            .map(NonEntityState::new)
            .toList();
    }
    
    @GetMapping("/statesFromRandomnessService")
    public List<NonEntityState> getStatesFromRandomnessService() {
        return randomnessService.getStates();
    }
    
    @GetMapping("/statesTableRowsHtml")
    public List<String> getStatesTableRowsHtml() {
        return adminService.getStatesTableRowsHtml();
    }
    
    
    private String deleteScoresAndGetMessage(boolean isForCapitalsOfStatesQuiz) {
        List<ScoreData> scoreDatasToDelete =
            scoreDataRepository.findByIsForCapitalsOfStatesQuiz(isForCapitalsOfStatesQuiz);
        int numScoreDatas = scoreDatasToDelete.size();
        scoreDataRepository.deleteAll(scoreDatasToDelete);
        return String.format("Successfully deleted %d scores.", numScoreDatas);
    }
    
    @DeleteMapping("/capitalsOfStatesQuizScores")
    public String deleteCapitalsOfStatesQuizScores() {
        return deleteScoresAndGetMessage(true);
    }
    
    @DeleteMapping("/statesOfCapitalsQuizScores")
    public String deleteStatesOfCapitalsQuizScores() {
        return deleteScoresAndGetMessage(false);
    }
}
