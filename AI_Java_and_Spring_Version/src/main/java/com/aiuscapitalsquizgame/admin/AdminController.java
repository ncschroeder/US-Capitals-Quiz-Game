package com.aiuscapitalsquizgame.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import com.aiuscapitalsquizgame.quizzes.QuizType;
import com.aiuscapitalsquizgame.scores.ScoreController;
import com.aiuscapitalsquizgame.scores.ScoreRepository;
import com.aiuscapitalsquizgame.states.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private StateRepository stateRepository;
    
    @Autowired
    private StateService stateService;
    
    @Autowired
    private ScoreRepository scoreRepository;

    @GetMapping
    public String admin() {
        return "admin";
    }
    
    @GetMapping("/states-from-database")
    public @ResponseBody List<State> getStatesFromDatabase() {
        return stateRepository.findAll();
    }

    @GetMapping("/states-from-state-service-class")
    public @ResponseBody List<State> getStatesFromStateServiceClass() {
        return stateService.getStates();
    }
    
    @DeleteMapping(ScoreController.CAPITALS_ENDPOINT)
    public @ResponseBody String deleteCapitalsScores() {
        return String.format("Successfully deleted %d scores.", scoreRepository.deleteByQuizType(QuizType.CAPITALS));
    }

    @DeleteMapping(ScoreController.STATES_ENDPOINT)
    public @ResponseBody String deleteStatesScores() {
        return String.format("Successfully deleted %d scores.", scoreRepository.deleteByQuizType(QuizType.STATES));
    }
}
