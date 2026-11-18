package com.uscapitalsquizgame.scoredata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.stream.Collectors;
import com.uscapitalsquizgame.quizzes.*;

@Controller
public class ScoreDataController {
    @Autowired
    private ScoreDataRepository scoreDataRepository;
    
    @Autowired
    private CapitalsOfStatesQuizManager capitalsOfStatesQuizManager;
    
    @Autowired
    private StatesOfCapitalsQuizManager statesOfCapitalsQuizManager;
    
    
    @PostMapping(CapitalsOfStatesQuizManager.SCORE_URL_END)
    public @ResponseBody String addCapitalsOfStatesScore(
        @RequestParam int score,
        @RequestParam String name
    ) {
        return addQuizScore(score, name, capitalsOfStatesQuizManager);
    }
    
    @PostMapping(StatesOfCapitalsQuizManager.SCORE_URL_END)
    public @ResponseBody String addStatesOfCapitalsScore(
        @RequestParam int score,
        @RequestParam String name
    ) {
        return addQuizScore(score, name, statesOfCapitalsQuizManager);
    }
    
    private String addQuizScore(int score, String name, QuizManager quizManager) {
        var scoreSaveDateTime = OffsetDateTime.now(ZoneOffset.UTC);
        ScoreData scoreData;
        
        try {
            scoreData = new ScoreData(score, name, quizManager, scoreSaveDateTime);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, null, ex);
        }
        
        scoreDataRepository.save(scoreData);
        return scoreSaveDateTime.toString();
    }
    
    
    @GetMapping(CapitalsOfStatesQuizManager.SCORES_URL_END)
    public String getCapitalsOfStatesScores(Model model) {
        return getScoresView(model, capitalsOfStatesQuizManager);
    }
    
    @GetMapping(StatesOfCapitalsQuizManager.SCORES_URL_END)
    public String getStatesOfCapitalsScores(Model model) {
        return getScoresView(model, statesOfCapitalsQuizManager);
    }
    
    private String getScoresView(Model model, QuizManager quizManager) {
        var scoreDataList =
            scoreDataRepository
            .findByQuizTypeOrderByScoreDescSaveDateTimeAsc(quizManager.getQuizTypeDbColValue());
        
        /*
        This is a JSON array that contains the numbers of milliseconds since
        the epoch for all ScoreDatas.
         */
        String scoreEpochMillisecondsJson =
            scoreDataList
            .stream()
            .mapToLong(data -> data.getSaveDateTime().toInstant().toEpochMilli())
            .mapToObj(Long::toString)
            .collect(Collectors.joining(",", "[", "]"));
        
        model
        .addAttribute("titleAndHeading", quizManager.getScoresTitleAndHeading())
        .addAttribute("scoreDataList", scoreDataList)
        .addAttribute("scoreEpochMillisecondsJson", scoreEpochMillisecondsJson);
        
        return "scores";
    }
}