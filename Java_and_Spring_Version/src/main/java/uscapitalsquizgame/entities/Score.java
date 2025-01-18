package uscapitalsquizgame.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    private int score;
    
    private String name;
    
    protected Score() {}
    
    public Score(int score, String name) {
        this.score = score;
        this.name = name;
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
    
    @Override
    public String toString() {
        return String.format("Score[id=%d, score=%d, name=%s]",id, score, name);
    }
}
