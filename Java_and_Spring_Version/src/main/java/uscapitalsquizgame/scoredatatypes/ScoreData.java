package uscapitalsquizgame.scoredatatypes;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import uscapitalsquizgame.QuizController;

@Entity
public class ScoreData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    private int score;
    
    @Column(length = 20)
    private String name;
    
    private OffsetDateTime dateTime;
    
    private boolean isForCapitalsOfStatesQuiz;
    
    protected ScoreData() {}
    
    public ScoreData(int score, String name, boolean isForCapitalsOfStatesQuiz) {
        if (score < 0 || score > QuizController.NUM_QUESTIONS || name.isBlank()) {
        
        }
        
        this.score = score;
        this.name = name.trim();
        this.dateTime = OffsetDateTime.now();
        this.isForCapitalsOfStatesQuiz = isForCapitalsOfStatesQuiz;
    }
    
    @Override
    public String toString() {
        return String.format(
            "Score[id=%d, score=%d, name=%s, dateTime=%s, isForCapitalsOfStatesQuiz=%s]",
            id, score, name, dateTime, isForCapitalsOfStatesQuiz
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
    
    public OffsetDateTime getDateTime() {
        return dateTime;
    }
    
    public boolean isForCapitalsOfStatesQuiz() {
        return isForCapitalsOfStatesQuiz;
    }
}
