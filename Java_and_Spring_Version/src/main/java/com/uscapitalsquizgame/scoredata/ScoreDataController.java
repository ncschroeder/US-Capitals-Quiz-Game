package com.uscapitalsquizgame.scoredata;

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
import com.uscapitalsquizgame.quizzes.*;

@Controller
public class ScoreDataController {
    @Autowired
    private ScoreDataRepository scoreDataRepository;
    
    @Autowired
    private ObjectMapper jsonObjectMapper;
    
    
    @PostMapping("/capitals-of-states-quiz-score")
    public @ResponseBody String addCapitalsOfStatesScore(
        @RequestParam int score,
        @RequestParam String name
    ) {
        return addQuizScore(score, name, CapitalsOfStatesQuizManager.getInstance());
    }
    
    @PostMapping("/states-of-capitals-quiz-score")
    public @ResponseBody String addStatesOfCapitalsScore(
        @RequestParam int score,
        @RequestParam String name
    ) {
        return addQuizScore(score, name, StatesOfCapitalsQuizManager.getInstance());
    }
    
    private String addQuizScore(int score, String name, QuizManager quizManager) {
        var dateTime = OffsetDateTime.now(ZoneOffset.UTC);
        ScoreData scoreData;
        
        try {
            scoreData = new ScoreData(score, name, quizManager, dateTime);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, null, ex);
        }
        
        scoreDataRepository.save(scoreData);
        return dateTime.toString();
    }
    
    @GetMapping("/statetest")
//    public @ResponseBody String test(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)ZonedDateTime dateTime) throws JsonProcessingException {
    public @ResponseBody List<State> test() {
//        testRepository.save(new TestClass());
//        return testRepository;
//        scoreDataRepository.save(new ScoreData(1, "n", true, OffsetDateTime.now()));
//        System.out.println(dateTime);
    
    @GetMapping("/capitals-of-states-quiz-scores")
    public String getCapitalsOfStatesScores(Model model) {
        return getScoresView(model, CapitalsOfStatesQuizManager.getInstance());
    }
    
    @GetMapping("/states-of-capitals-quiz-scores")
    public String getStatesOfCapitalsScores(Model model) {
        return getScoresView(model, StatesOfCapitalsQuizManager.getInstance());
    }
    
    private String getScoresView(Model model, QuizManager quizManager) {
        var scoreDataList =
            scoreDataRepository
            .findByQuizTypeOrderByScoreDescDateTimeAsc(quizManager.getQuizTypeDbColValue());
        
        List<String> dateTimeStrings =
            scoreDataList
            .stream()
            .map(data -> data.getDateTime().toString())
            .toList();
        
        String dateTimeStringsJson;
        try {
            dateTimeStringsJson = jsonObjectMapper.writeValueAsString(dateTimeStrings);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
        
        model
        .addAttribute("titleAndHeading", quizManager.getScoresTitleAndHeading())
        .addAttribute("scoreDataList", scoreDataList)
        .addAttribute("dateTimeStringsJson", dateTimeStringsJson);
        
        return "scores";
    }
}


//    @GetMapping("/capitalsOfStatesQuizScores")
//    public String getCapitalsOfStatesQuizScores(Model model) {
//        return updateModelAndGetView(model, true);
//    }
//
//    @GetMapping("/statesOfCapitalsQuizScores")
//    public String getStatesOfCapitalsQuizScores(Model model) {
//        return updateModelAndGetView(model, false);
//    }

//    @PostMapping("/capitalsOfStatesQuizScore")
//    public @ResponseBody String addCapitalsOfStatesQuizScore(@RequestParam int score, @RequestParam String name) {
////        System.out.println("got score: " + score + ' ' + name);
//        return saveScoreAndGetDateTime(score, name, true);
//    }

//        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime dateTime


//    @PostMapping("/statesOfCapitalsQuizScore")
//    public @ResponseBody String addStatesOfCapitalsQuizScore(@RequestParam int score, @RequestParam String name) {
//        return saveScoreAndGetDateTime(score, name, false);
////        scoreDataRepository.save(new ScoreData(score, name, false));
//    }