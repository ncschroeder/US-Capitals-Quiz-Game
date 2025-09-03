package uscapitalsquizgame.scoredatatypes;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import uscapitalsquizgame.QuizController;

@Entity
public class ScoreData {
    public static final int MAX_NAME_LENGTH = 20;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    private int score;
    
    @Column(length = MAX_NAME_LENGTH)
    private String name;
    
    private boolean isForCapitalsOfStatesQuiz;
    
    private OffsetDateTime dateTime;
    
    protected ScoreData() {}
    
    public ScoreData(int score, String name, boolean isForCapitalsOfStatesQuiz, OffsetDateTime dateTime) {
        if (score < 0 || score > QuizController.NUM_QUESTIONS || name.isBlank()) {
        
        }
        
        this.score = score;
        this.name = name.strip();
        this.isForCapitalsOfStatesQuiz = isForCapitalsOfStatesQuiz;
        this.dateTime = dateTime;
    }
    
    @Override
    public String toString() {
        return String.format(
            "ScoreData[id=%d, score=%d, name=%s, isForCapitalsOfStatesQuiz=%s, dateTime=%s]",
            id, score, name, isForCapitalsOfStatesQuiz, dateTime
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
    
    public boolean isForCapitalsOfStatesQuiz() {
        return isForCapitalsOfStatesQuiz;
    }
    
    public OffsetDateTime getDateTime() {
        return dateTime;
    }
    
    }
