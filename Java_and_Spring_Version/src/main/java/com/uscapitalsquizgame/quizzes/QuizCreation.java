package com.uscapitalsquizgame.quizzes;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import com.uscapitalsquizgame.states.State;
import com.uscapitalsquizgame.states.StateRepository;

/**
 * This is a utility class that's mostly used by QuizManager.createQuestionDataListForQuiz and
 * the implementations of QuizManager.createQuestionData.
 */
public class QuizCreation {
    public static final int NUM_QUESTIONS = 10;
    
    /**
     * The number of answer choices including "None of the above".
     */
    static final int NUM_ANSWER_CHOICES = 5;
    
    /**
     * This is explicitly an ArrayList since we'll be accessing random states from it.
     */
    private static ArrayList<State> states;
    
    static final Random random = new Random();
    
    public static List<State> getStates() {
        return List.copyOf(states);
    }
    
    public static void setStates(StateRepository stateRepository) {
        states = new ArrayList<>(stateRepository.findAll());
    }
    
    @Configuration
    public static class CommandLineRunnerConfig {
        /**
         * This bean makes the code in the lambda below run on startup.
         */
        @Bean
        public CommandLineRunner initStatesRunner(StateRepository stateRepository) {
            return args -> {
                setStates(stateRepository);
            };
        }
    }
    
    private static int getRandomIndex(List<?> list) {
        return random.nextInt(list.size());
    }
    
    static <T> T getRandomElement(List<T> list) {
        return list.get(getRandomIndex(list));
    }
    
    private static State getRandomState() {
        return getRandomElement(states);
    }
    
    static Stream<State> getStreamOfRandomStates() {
        return
            IntStream.generate(() -> getRandomIndex(states))
            .distinct()
            .mapToObj(states::get);
    }
    
    static String getRandomCapital() {
        return getRandomState().getCapital();
    }
    
    static String getRandomCity() {
        return getRandomElement(getRandomState().getCities());
    }
}