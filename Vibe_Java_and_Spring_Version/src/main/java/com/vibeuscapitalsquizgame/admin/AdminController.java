package com.vibeuscapitalsquizgame.admin;

import com.vibeuscapitalsquizgame.scores.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import com.vibeuscapitalsquizgame.quizzes.QuizType;
import com.vibeuscapitalsquizgame.scores.ScoreController;
import com.vibeuscapitalsquizgame.states.*;

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
    public List<State> getStatesFromDatabase() {
        return stateRepository.findAll();
    }
    
    @GetMapping("/states-from-state-service-class")
    public List<State> getStatesFromStateServiceClass() {
        return stateService.getStates();
    }

    @GetMapping("/states-table-rows-markdown")
    public @ResponseBody String statesTableRowsMarkdown() {
        return
            stateRepository
            .findAll()
            .stream()
            .sorted(Comparator.comparing(State::getName))
            .map(s -> String.format(
                "| %s | %s | %s |",
                s.getName(),
                s.getCapital(),
                s.getCities().stream().sorted().collect(Collectors.joining(", "))
            ))
            .collect(Collectors.joining("\n"));
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
