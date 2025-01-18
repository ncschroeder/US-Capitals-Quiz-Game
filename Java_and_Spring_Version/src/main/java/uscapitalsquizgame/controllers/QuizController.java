package uscapitalsquizgame.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.function.Function;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uscapitalsquizgame.entities.State;
import uscapitalsquizgame.questionclasses.*;
import uscapitalsquizgame.repositories.StatesRepository;
import uscapitalsquizgame.repositories.NonCapitalCitiesRepository;

@Controller
public class QuizController {
    @Autowired
    private StatesRepository statesRepository;
    
    @Autowired
    private NonCapitalCitiesRepository nonCapitalCitiesRepository;
    
    private final ObjectMapper jsonObjectMapper = new ObjectMapper();
    
    private String updateModelAndGetView(
        Model model,
        String titleAndHeading,
        Function<State, Question> createQuestion
    ) throws JsonProcessingException {
        List<Question> questions =
            getStreamOfRandomStates(statesRepository, 10)
            .map(createQuestion)
            .toList();
        
        model
        .addAttribute("titleAndHeading", titleAndHeading)
        .addAttribute("questionsJson", jsonObjectMapper.writeValueAsString(questions));
        
        return "quiz";
    }
    
    @GetMapping("/capitalsOfStatesQuiz")
    public String capitalsOfStatesQuiz(Model model) throws JsonProcessingException {
        Function<State, Question> createCapitalOfStateQuestion =
            state -> new CapitalOfStateQuestion(state, statesRepository, nonCapitalCitiesRepository);
        return updateModelAndGetView(model, "Capitals of States Quiz", createCapitalOfStateQuestion);
    }
    
    @GetMapping("/statesOfCapitalsQuiz")
    public String statesOfCapitalsQuiz(Model model) throws JsonProcessingException {
        Function<State, Question> createStateOfCapitalQuestion =
            state -> new StateOfCapitalQuestion(state, statesRepository);
        return updateModelAndGetView(model, "States of Capitals Quiz", createStateOfCapitalQuestion);
    }
}
