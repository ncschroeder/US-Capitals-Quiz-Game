package uscapitalsquizgame.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;
import uscapitalsquizgame.entities.NonCapitalCity;

public interface NonCapitalCitiesRepository extends CrudRepository<NonCapitalCity, Integer> {
    Streamable<NonCapitalCity> findByStateId(int stateId);
}
