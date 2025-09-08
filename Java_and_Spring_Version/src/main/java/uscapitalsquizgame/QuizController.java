package uscapitalsquizgame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uscapitalsquizgame.questions.*;
import uscapitalsquizgame.scoredata.ScoreData;
import uscapitalsquizgame.states.State;

@Controller
public class QuizController {
    public static final int NUM_QUESTIONS = 10;
    
    public static String getQuizType(boolean isCapitalsOfStatesQuiz) {
        return isCapitalsOfStatesQuiz ? "Capitals of States" : "States of Capitals";
    }
    
    @Autowired
    private RandomnessService randomnessService;
    
    @Autowired
    private ObjectMapper jsonObjectMapper;
    
    private String updateModelAndGetView(
        Model model,
        boolean isCapitalsOfStatesQuiz,
        BiFunction<State, RandomnessService, Question> createQuestion
    ) {
        String quizType = getQuizType(isCapitalsOfStatesQuiz);
        
        List<Question> questions =
            randomnessService
            .getStreamOfRandomStates(NUM_QUESTIONS)
            .map(state -> createQuestion.apply(state, randomnessService))
            .toList();
        
        String questionsJson;
        try {
            questionsJson = jsonObjectMapper.writeValueAsString(questions);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
        
        model.addAllAttributes(
            Map.of(
                "quizType", quizType,
                "titleAndHeading", quizType + " Quiz",
                "isCapitalsOfStatesQuiz", Boolean.toString(isCapitalsOfStatesQuiz),
                "maxNameLength", ScoreData.MAX_NAME_LENGTH,
                "questionsJson", questionsJson
            )
        );
        
        return "quiz";
    }
    
    @GetMapping("/capitalsOfStatesQuiz")
    public String capitalsOfStatesQuiz(Model model) {
        return updateModelAndGetView(model, true, CapitalsOfStatesQuestion::new);
    }
    
    @GetMapping("/statesOfCapitalsQuiz")
    public String statesOfCapitalsQuiz(Model model) {
        return updateModelAndGetView(model, false, StatesOfCapitalsQuestion::new);
    }
}
