package uscapitalsquizgame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.*;
import uscapitalsquizgame.states.State;
import uscapitalsquizgame.states.StateRepository;

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
    
    public Stream<State> getStreamOfRandomStates(int numStates) {
        return
            IntStream.generate(() -> getRandomIndex(states))
            .distinct()
            .mapToObj(states::get)
            .limit(numStates);
    }
    
    public String getRandomCapital() {
        return getRandomState().getCapital();
    }
    
    public String getRandomCity() {
        return getRandomElement(getRandomState().getCities());
    }
}
