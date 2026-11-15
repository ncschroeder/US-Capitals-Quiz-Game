package com.aiuscapitalsquizgame.scores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import com.aiuscapitalsquizgame.quizzes.QuizController;
import com.aiuscapitalsquizgame.quizzes.QuizType;

@Controller
public class ScoreController {
    public static final String CAPITALS_ENDPOINT = "/scores/capitals";
    public static final String STATES_ENDPOINT = "/scores/states";
    public static final int MIN_NAME_LENGTH = 1;
    public static final int MAX_NAME_LENGTH = 25;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public record SubmitScoreRequest(String name, int score, QuizType quizType) {}

    public record SubmitScoreResponse(OffsetDateTime submittedAt) {}

    @PostMapping("/scores")
    public @ResponseBody SubmitScoreResponse submit(@RequestBody SubmitScoreRequest request) {
        if (request.score() < 0 || request.score() > QuizController.QUESTION_COUNT) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                String.format("Score must be between 0 and %d", QuizController.QUESTION_COUNT)
            );
        }
        
        String name = request.name().trim();
        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                String.format("Name must be %d to %d characters", MIN_NAME_LENGTH, MAX_NAME_LENGTH)
            );
        }
        
        var score =
            new Score(
                name,
                request.score(),
                request.quizType(),
                OffsetDateTime.now(ZoneOffset.UTC)
            );
        scoreRepository.save(score);
        return new SubmitScoreResponse(score.getSubmittedAt());
    }

    @GetMapping(CAPITALS_ENDPOINT)
    public String capitalsScores(Model model) throws JacksonException {
        return renderScores(model, QuizType.CAPITALS, "Scores for capitals of states quizzes");
    }

    @GetMapping(STATES_ENDPOINT)
    public String statesScores(Model model) throws JacksonException {
        return renderScores(model, QuizType.STATES, "Scores for states of capitals quizzes");
    }

    private String renderScores(Model model, QuizType quizType, String heading) throws JacksonException {
        List<Score> scores = scoreRepository.findByQuizTypeOrderByScoreDescSubmittedAtAsc(quizType);
        List<String> submittedAts =
            scores
            .stream()
            .map(s -> s.getSubmittedAt().toString())
            .toList();
        
        model
        .addAttribute("heading", heading)
        .addAttribute("scores", scores)
        .addAttribute("submittedAtsJson", objectMapper.writeValueAsString(submittedAts));
        
        return "scores";
    }
}