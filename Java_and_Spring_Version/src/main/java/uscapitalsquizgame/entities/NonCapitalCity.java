package uscapitalsquizgame.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class NonCapitalCity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    private Integer stateId;
    
    private String name;
    
    protected NonCapitalCity() {}
    
    public NonCapitalCity(Integer stateId, String name) {
        this.stateId = stateId;
        this.name = name;
    }
    
    @Override
    public String toString() {
        return String.format("NonCapitalCity[id=%d, stateId=%d, name=%s]", id, stateId, name);
    }
    
    public String getName() {
        return name;
    }
    
    public Integer getId() {
        return id;
    }
}
