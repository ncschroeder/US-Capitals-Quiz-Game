package com.vibeuscapitalsquizgame.scores;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import com.vibeuscapitalsquizgame.quizzes.QuizController;
import com.vibeuscapitalsquizgame.quizzes.QuizType;

@RestController
public class ScoreController {
    public static final int MIN_NAME_LENGTH = 1;
    public static final int MAX_NAME_LENGTH = 25;

    private final ScoreRepository scoreRepository;

    public ScoreController(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public record SubmitScoreRequest(String name, int score, QuizType quizType) {}

    public record SubmitScoreResponse(OffsetDateTime submittedAt) {}

    @PostMapping("/scores")
    public SubmitScoreResponse submit(@RequestBody SubmitScoreRequest request) {
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
}