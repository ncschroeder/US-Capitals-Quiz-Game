package com.uscapitalsquizgame.states;

import org.springframework.data.repository.ListCrudRepository;

public interface StateRepository extends ListCrudRepository<State, Integer> {}
//    Streamable<State> findAll();
//    void saveAll(Iterable<State> states);
//    void deleteAll();