package com.uscapitalsquizgame.states;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String name;
    
    private String capital;
    
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "city", joinColumns = @JoinColumn(name = "state_id"))
    @Column(name = "name")
    private List<String> cities;
    
    protected State() {}
    
    public State(String name, String capital, List<String> cities) {
        this.name = name;
        this.capital = capital;
        this.cities = List.copyOf(cities);
    }
    
    @Override
    public String toString() {
        return String.format("State[id=%d, name=%s, capital=%s, cities=%s]", id, name, capital, cities);
    }
    
    @Override
    public boolean equals(Object obj) {
        return
            obj instanceof State state && this.name.equals(state.name) &&
            this.capital.equals(state.capital) && this.cities.equals(state.cities);
    }
    
    @JsonIgnore
    public Integer getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getCapital() {
        return capital;
    }
    
    public List<String> getCities() {
        return cities;
    }
}