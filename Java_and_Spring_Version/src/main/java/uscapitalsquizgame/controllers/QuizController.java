package uscapitalsquizgame.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.function.BiFunction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uscapitalsquizgame.RandomnessService;
import uscapitalsquizgame.questionclasses.*;
import uscapitalsquizgame.statetypes.NonEntityState;

@Controller
public class QuizController {
    
    @Autowired
    private RandomnessService randomnessService;
    
    private final ObjectMapper jsonObjectMapper = new ObjectMapper();
    
    private String updateModelAndGetView(
        Model model,
        String titleAndHeading,
    ) throws JsonProcessingException {
        BiFunction<NonEntityState, RandomnessService, Question> createQuestion
        List<Question> questions =
            randomnessService
            .getStreamOfRandomStates(NUM_QUESTIONS)
            .map(state -> createQuestion.apply(state, randomnessService))
            .toList();
        
        model
        .addAttribute("titleAndHeading", titleAndHeading)
        .addAttribute("questionsJson", jsonObjectMapper.writeValueAsString(questions));
        
        return "quiz";
    }
    
    @GetMapping("/capitalsOfStatesQuiz")
    public String capitalsOfStatesQuiz(Model model) throws JsonProcessingException {
        return updateModelAndGetView(model, "Capitals of States Quiz", CapitalOfStateQuestion::new);
    }
    
    @GetMapping("/statesOfCapitalsQuiz")
    public String statesOfCapitalsQuiz(Model model) throws JsonProcessingException {
        return updateModelAndGetView(model, "States of Capitals Quiz", StateOfCapitalQuestion::new);
    }
}
