package com.uscapitalsquizgame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import com.uscapitalsquizgame.states.State;
import com.uscapitalsquizgame.states.StateRepository;

@Service
public class RandomnessService {
    @Autowired
    private StateRepository stateRepository;
    
    /**
     * This is an ArrayList since we're accessing random states from it.
     */
    private ArrayList<State> states;
    
    @PostConstruct
    public void updateStates() {
        states = new ArrayList<>(stateRepository.findAll());
    }
    
    public List<State> getStates() {
        return List.copyOf(states);
    }
    
    public final Random random = new Random();
    
    public boolean thisHasApproximatelyAOneInFiveChanceOfReturningTrue() {
        return random.nextInt(5) == 0;
    }
    
    private int getRandomIndex(List<?> list) {
        return random.nextInt(list.size());
    }
    
    public <T> T getRandomElement(List<T> list) {
        return list.get(getRandomIndex(list));
    }
    
    private State getRandomState() {
        return getRandomElement(states);
    }
    
    public Stream<State> getStreamOfRandomStates() {
        return
            IntStream.generate(() -> getRandomIndex(states))
            .distinct()
            .mapToObj(states::get);
    }
    
    public String getRandomCapital() {
        return getRandomState().getCapital();
    }
    
    public String getRandomCity() {
        return getRandomElement(getRandomState().getCities());
    }
}
