package uscapitalsquizgame.repositories;

import org.springframework.data.repository.ListCrudRepository;
import uscapitalsquizgame.entities.State;

public interface StatesRepository extends ListCrudRepository<State, Integer> {
}
