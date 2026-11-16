package com.uscapitalsquizgame.scoredata;

import com.uscapitalsquizgame.quizzes.QuizCreation;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import com.uscapitalsquizgame.quizzes.QuizManager;

@Entity
public class ScoreData {
    public static final int MAX_NAME_LENGTH = 25;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private int score;
    
    private String name;
    
    private String quizType;
    
    private OffsetDateTime saveDateTime;
    
    protected ScoreData() {}
    
    public ScoreData(int score, String name, QuizManager quizManager, OffsetDateTime saveDateTime) {
        this.name = name.strip();
        
        if (
            score < 0 || score > QuizCreation.NUM_QUESTIONS ||
            this.name.isEmpty() || this.name.length() > MAX_NAME_LENGTH
        ) {
            throw new IllegalArgumentException();
        }
        
        this.score = score;
        this.quizType = quizManager.getQuizTypeDbColValue();
        this.saveDateTime = saveDateTime;
    }
    
    @Override
    public String toString() {
        return String.format(
            "ScoreData[id=%d, score=%d, name=%s, quizType=%s, saveDateTime=%s]",
            id, score, name, quizType, saveDateTime
        );
    }
    
    public Integer getId() {
        return id;
    }
    
    public int getScore() {
        return score;
    }
    
    public String getName() {
        return name;
    }
    
    public String getQuizType() {
        return quizType;
    }
    
    public OffsetDateTime getSaveDateTime() {
        return saveDateTime;
    }
}