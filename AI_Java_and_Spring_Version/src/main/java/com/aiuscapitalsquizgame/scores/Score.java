package com.aiuscapitalsquizgame.scores;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import com.aiuscapitalsquizgame.quizzes.QuizType;

@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private int score;

    @Enumerated(EnumType.STRING)
    private QuizType quizType;

    private OffsetDateTime submittedAt;

    protected Score() {}

    public Score(String name, int score, QuizType quizType, OffsetDateTime submittedAt) {
        this.name = name;
        this.score = score;
        this.quizType = quizType;
        this.submittedAt = submittedAt;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public QuizType getQuizType() {
        return quizType;
    }

    public OffsetDateTime getSubmittedAt() {
        return submittedAt;
    }
}