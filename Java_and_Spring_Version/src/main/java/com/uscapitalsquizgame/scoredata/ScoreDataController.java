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
import com.uscapitalsquizgame.QuizController;
import com.uscapitalsquizgame.states.State;
import com.uscapitalsquizgame.states.StateRepository;

@Controller
public class ScoreDataController {
    @Autowired
    private ScoreDataRepository scoreDataRepository;
    
    @Autowired
    private StateRepository stateRepository;
    
    @Autowired
    private ObjectMapper jsonObjectMapper;
    
    @GetMapping("/quizScores")
    public String quizScores(
        Model model,
        @RequestParam(name = "forCapitalsOfStatesQuizzes") boolean getCapitalsOfStatesQuizScores
    ) throws JsonProcessingException {
        String titleAndHeading =
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
        .addAttribute("titleAndHeading", titleAndHeading)
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
    
    @GetMapping("/statetest")
//    public @ResponseBody String test(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)ZonedDateTime dateTime) throws JsonProcessingException {
    public @ResponseBody List<State> test() {
//        testRepository.save(new TestClass());
//        return testRepository;
//        scoreDataRepository.save(new ScoreData(1, "n", true, OffsetDateTime.now()));
//        System.out.println(dateTime);
        
        stateRepository.save(new State("name", "capital", List.of("city")));
        return stateRepository.findAll();
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