package uscapitalsquizgame.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class State {
    @Id
    private Integer id;
    
    private String name;
    
    private String capital;
    
    protected State() {}
    
    public State(Integer id, String name, String capital) {
        this.id = id;
        this.name = name;
        this.capital = capital;
    }
    
    @Override
    public String toString() {
        return String.format("State[id=%d, name=%s, capital=%s]", id, name, capital);
    }
    
    public Integer getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getCapital() {
        return capital;
    }
}
