package com.uscapitalsquizgame.scoredata;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import com.uscapitalsquizgame.QuizController;

@Entity
//@Table(name = "scoreData")
public class ScoreData {
    public static final int MAX_NAME_LENGTH = 20;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
//    @Column(nullable = false, updatable = false)
    private int score;
    
//    @Column(length = MAX_NAME_LENGTH, nullable = false, updatable = false)
    private String name;
    
//    @Column(nullable = false, updatable = false)
    private boolean isForCapitalsOfStatesQuiz;
    
//    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateTime;
    
    protected ScoreData() {}
    
    public ScoreData(int score, String name, boolean isForCapitalsOfStatesQuiz, OffsetDateTime dateTime) {
        this.name = name.strip();
        
        if (
            score < 0 || score > QuizController.NUM_QUESTIONS ||
            this.name.isEmpty() || this.name.length() > MAX_NAME_LENGTH
        ) {
            throw new IllegalArgumentException();
        }
        
        this.score = score;
        this.isForCapitalsOfStatesQuiz = isForCapitalsOfStatesQuiz;
        this.dateTime = dateTime;
    }
    
    @Override
    public String toString() {
        return String.format(
            "ScoreData[id=%d, score=%d, name=%s, isForCapitalsOfStatesQuiz=%s, dateTime=%s]",
            0, score, name, isForCapitalsOfStatesQuiz, dateTime
        );
    }
    
//    public Integer getId() {
//        return id;
//    }
    
    public int getScore() {
        return score;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean isForCapitalsOfStatesQuiz() {
        return isForCapitalsOfStatesQuiz;
    }
    
    public OffsetDateTime getDateTime() {
        return dateTime;
    }
}