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
import uscapitalsquizgame.statetypes.*;

@Service
public class RandomnessService {
    @Autowired
    private StatesRepository statesRepository;
    
    private List<NonEntityState> states;
    
    @PostConstruct
    public void updateStates() {
        states =
            statesRepository
            .findAll()
            .stream()
            .map(NonEntityState::new)
            .toList();
    public List<NonEntityState> getStates() {
        return states;
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
    
    private NonEntityState getRandomState() {
        return getRandomElement(states);
    }
    
    public Stream<NonEntityState> getStreamOfRandomStates(int numStates) {
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
