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
    private CapitalsOfStatesQuizManager capitalsOfStatesQuizManager;
    
    @Autowired
    private StatesOfCapitalsQuizManager statesOfCapitalsQuizManager;
    
    @Autowired
    private RandomnessService randomnessService;
    
    @Autowired
    private ObjectMapper jsonObjectMapper;
    
    
    @GetMapping(CapitalsOfStatesQuizManager.QUIZ_URL_END)
    public String getCapitalsOfStatesQuizView(Model model) {
        return getQuizView(model, capitalsOfStatesQuizManager);
    }
    
    @GetMapping(StatesOfCapitalsQuizManager.QUIZ_URL_END)
    public String getStatesOfCapitalsQuizView(Model model) {
        return getQuizView(model, statesOfCapitalsQuizManager);
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
                "maxNameLength", ScoreData.MAX_NAME_LENGTH,
                "addScoreUrlEnd", quizManager.getAddScoreUrlEnd(),
                "takeAnotherQuizText", quizManager.getTakeAnotherQuizText(),
                "quizUrlEnd", quizManager.getQuizUrlEnd(),
                "seeScoresText", quizManager.getSeeScoresText(),
                "seeScoresUrlEnd", quizManager.getSeeScoresUrlEnd()
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