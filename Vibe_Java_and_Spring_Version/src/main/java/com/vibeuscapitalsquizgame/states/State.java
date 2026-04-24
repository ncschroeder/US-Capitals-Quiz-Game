package com.vibeuscapitalsquizgame.states;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String capital;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "city", joinColumns = @JoinColumn(name = "state_id"))
    @Column(name = "name")
    private List<String> cities;

    protected State() {}

    public State(String name, String capital, List<String> cities) {
        this.name = name;
        this.capital = capital;
        this.cities = List.copyOf(cities);
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

    public List<String> getCities() {
        return cities;
    }
}
