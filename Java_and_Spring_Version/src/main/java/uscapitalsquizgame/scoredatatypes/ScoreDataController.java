package uscapitalsquizgame.scoredatatypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ScoreDataController {
    @Autowired
    private ScoreDataRepository scoreDataRepository;
    
    @Autowired
    private ObjectMapper jsonObjectMapper;
    
    private String updateModelAndGetView(Model model, boolean getCapitalsOfStatesQuizScores) {
        List<ScoreData> scoreDataList =
            scoreDataRepository
            .findAllByIsForCapitalsOfStatesQuizOrderByScoreDescDateTimeAsc(getCapitalsOfStatesQuizScores);
        
        List<String> dateTimeStrings =
            scoreDataList
            .stream()
            .map(sd -> sd.getDateTime().toString())
            .toList();
        
        String dateTimeStringsJson;
        try {
            dateTimeStringsJson = jsonObjectMapper.writeValueAsString(dateTimeStrings);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
        
        var titleAndCaption =
            (getCapitalsOfStatesQuizScores ? "Capitals of States" : "States of Capitals") + " Quiz Scores";
        
        model
        .addAttribute("titleAndCaption", titleAndCaption)
        .addAttribute("scoreDataList", scoreDataList)
        .addAttribute("dateTimeStringsJson", dateTimeStringsJson);
        
        return "scores";
    }
    
    @GetMapping("/capitalsOfStatesQuizScores")
    public String getCapitalsOfStatesQuizScores(Model model) {
        return updateModelAndGetView(model, true);
    }
    
    @GetMapping("/statesOfCapitalsQuizScores")
    public String getStatesOfCapitalsQuizScores(Model model) {
        return updateModelAndGetView(model, false);
    }
    
    @PostMapping("/capitalsOfStatesQuizScore")
    public void addCapitalsOfStatesQuizScore(@RequestParam int score, @RequestParam String name) {
        scoreDataRepository.save(new ScoreData(score, name, true));
    }
    
    @PostMapping("/statesOfCapitalsQuizScore")
    public void addStatesOfCapitalsQuizScore(@RequestParam int score, @RequestParam String name) {
        scoreDataRepository.save(new ScoreData(score, name, false));
    }
}