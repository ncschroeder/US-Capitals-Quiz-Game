package com.uscapitalsquizgame.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import com.uscapitalsquizgame.quizzes.*;
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
    private CapitalsOfStatesQuizManager capitalsOfStatesQuizManager;
    
    @Autowired
    private StatesOfCapitalsQuizManager statesOfCapitalsQuizManager;
    
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
        QuizCreation.setStates(stateRepository);
        return String.format(
            "Update successful. There are now %d states in the database.", states.size()
        );
    
    
    @GetMapping("/states-from-database")
    public List<State> getStatesFromDatabase() {
        return stateRepository.findAll();
    }
    
    @GetMapping("/states-from-quiz-creation-class")
    public List<State> getStatesFromQuizCreationClass() {
        return QuizCreation.getStates();
    }
    
    
    @DeleteMapping(CapitalsOfStatesQuizManager.SCORES_URL_END)
    public @ResponseBody String deleteCapitalsOfStatesScores() {
        return deleteScores(capitalsOfStatesQuizManager);
    }
    
    @DeleteMapping(StatesOfCapitalsQuizManager.SCORES_URL_END)
    public @ResponseBody String deleteStatesOfCapitalsScores() {
        return deleteScores(statesOfCapitalsQuizManager);
    }
    
    private String deleteScores(QuizManager quizManager) {
        List<ScoreData> scoreDatasToDelete =
            scoreDataRepository.findByQuizType(quizManager.getQuizTypeDbColValue());
        scoreDataRepository.deleteAll(scoreDatasToDelete);
        return String.format("Successfully deleted %d scores.", scoreDatasToDelete.size());
    }
}