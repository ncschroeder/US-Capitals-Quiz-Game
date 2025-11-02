package com.uscapitalsquizgame.quizzes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uscapitalsquizgame.scoredata.ScoreData;
import com.uscapitalsquizgame.RandomnessService;

@Controller
public class QuizController {
    @Autowired
    private RandomnessService randomnessService;
    
    @Autowired
    private ObjectMapper jsonObjectMapper;
    
    @GetMapping("/capitals-of-states-quiz")
    public String capitalsOfStatesQuiz(Model model) {
        return getQuizView(model, CapitalsOfStatesQuizManager.getInstance());
    }
    
    @GetMapping("/states-of-capitals-quiz")
    public String statesOfCapitalsQuiz(Model model) {
        return getQuizView(model, StatesOfCapitalsQuizManager.getInstance());
    }
    
    private String getQuizView(Model model, QuizManager quizManager) {
        var questionDataList = quizManager.createQuestionDataListForQuiz(randomnessService);
        
        String questionDataArrJson;
        try {
            questionDataArrJson = jsonObjectMapper.writeValueAsString(questionDataList);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
        
        model.addAllAttributes(
            Map.of(
                "titleAndHeading", quizManager.getQuizTitleAndHeading(),
                "questionDataArrJson", questionDataArrJson,
                "quizUrl", quizManager.getQuizUrl(),
                "takeAnotherQuizText", quizManager.getTakeAnotherQuizText(),
                "maxNameLength", ScoreData.MAX_NAME_LENGTH,
                "addScoreUrl", quizManager.getAddScoreUrl(),
                "seeScoresUrl", quizManager.getSeeScoresUrl(),
                "seeScoresUrlText", quizManager.getSeeScoresUrlText()
            )
        );
        
        return "quiz";
    }
    public String capitalsOfStatesQuiz(Model model) {
//        System.out.println("test request received");
//        return "test request received";
//jsonObjectMapper.writeValueAsString(List.of(1));
//    }

//    private final Function<State, Question> createCapitalOfStateQuestion =
//        state -> new CapitalOfStateQuestion(state, statesRepository, nonCapitalCitiesRepository);

//    private final Function<State, Question> createStateOfCapitalQuestion =
//        state -> new StateOfCapitalQuestion(state, statesRepository);