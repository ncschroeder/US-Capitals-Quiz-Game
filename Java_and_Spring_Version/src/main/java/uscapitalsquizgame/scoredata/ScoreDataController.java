package uscapitalsquizgame.scoredata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uscapitalsquizgame.QuizController;

@Controller
public class ScoreDataController {
    @Autowired
    private ScoreDataRepository scoreDataRepository;
    
    @Autowired
    private ObjectMapper jsonObjectMapper;
    
    @GetMapping("/quizScores")
    public String quizScores(
        Model model,
        @RequestParam(name = "forCapitalsOfStatesQuizzes") boolean getCapitalsOfStatesQuizScores
    ) throws JsonProcessingException {
        String titleAndCaption =
            QuizController.getQuizType(getCapitalsOfStatesQuizScores) + " Quiz Scores";
        
        var scoreDataList =
            scoreDataRepository
            .findByIsForCapitalsOfStatesQuizOrderByScoreDescDateTimeAsc(getCapitalsOfStatesQuizScores);
        
        List<String> dateTimeStrings =
            scoreDataList
            .stream()
            .map(sd -> sd.getDateTime().toString())
            .toList();
        
        String dateTimeStringsJson = jsonObjectMapper.writeValueAsString(dateTimeStrings);;
        
        model
        .addAttribute("titleAndCaption", titleAndCaption)
        .addAttribute("scoreDataList", scoreDataList)
        .addAttribute("dateTimeStringsJson", dateTimeStringsJson);
        
        return "scores";
    }
    
    @PostMapping("/quizScore")
    public @ResponseBody String addQuizScore(
        @RequestParam int score, @RequestParam String name, @RequestParam boolean isForCapitalsOfStatesQuiz
    ) {
        var dateTime = OffsetDateTime.now(ZoneOffset.UTC);
        ScoreData scoreData;
        
        try {
            scoreData = new ScoreData(score, name, isForCapitalsOfStatesQuiz, dateTime);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, null, ex);
        }
        
        scoreDataRepository.save(scoreData);
        return dateTime.toString();
    }
}
