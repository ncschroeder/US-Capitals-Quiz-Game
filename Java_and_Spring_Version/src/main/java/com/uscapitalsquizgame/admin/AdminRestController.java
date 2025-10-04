package com.uscapitalsquizgame.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import com.uscapitalsquizgame.RandomnessService;
import com.uscapitalsquizgame.scoredata.ScoreData;
import com.uscapitalsquizgame.scoredata.ScoreDataRepository;
import com.uscapitalsquizgame.states.State;
import com.uscapitalsquizgame.states.StateRepository;

@RestController
@RequestMapping("/admin")
public class AdminRestController {
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private StateRepository stateRepository;
    
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
        List<State> states = adminService.createStates(file);
        stateRepository.deleteAll();
        stateRepository.saveAll(states);
        randomnessService.updateStates();
        return String.format(
            "Update successful. There are now %d states in the database.", states.size()
        );
    
    
    @GetMapping("/statesFromDatabase")
    public List<State> getStatesFromDatabase() {
        return stateRepository.findAll();
    }
    
    @GetMapping("/statesFromRandomnessService")
    public List<State> getStatesFromRandomnessService() {
        return randomnessService.getStates();
    }
    
    @GetMapping("/statesTableRowsHtml")
    public List<String> getStatesTableRowsHtml() {
        return adminService.getStatesTableRowsHtml();
    }
    
    
    private String deleteScoresAndGetMessage(boolean isForCapitalsOfStatesQuiz) {
        List<ScoreData> scoreDatasToDelete =
            scoreDataRepository.findByIsForCapitalsOfStatesQuiz(isForCapitalsOfStatesQuiz);
        scoreDataRepository.deleteAll(scoreDatasToDelete);
        return String.format("Successfully deleted %d scores.", scoreDatasToDelete.size());
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
