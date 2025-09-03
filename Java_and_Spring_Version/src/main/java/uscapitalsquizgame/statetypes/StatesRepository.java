package uscapitalsquizgame.statetypes;

import org.springframework.data.repository.Repository;
import org.springframework.data.util.Streamable;

public interface StatesRepository extends Repository<EntityState, Integer> {
    Streamable<EntityState> findAll();
    
    void saveAll(Iterable<EntityState> states);
    
    void deleteAll();
}