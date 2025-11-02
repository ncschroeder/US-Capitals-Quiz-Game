package com.uscapitalsquizgame.quizzes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.uscapitalsquizgame.RandomnessService;
import com.uscapitalsquizgame.states.State;

public final class StatesOfCapitalsQuizManager extends QuizManager {
    private StatesOfCapitalsQuizManager() {
        super("States of Capitals");
    }
    
    private static final StatesOfCapitalsQuizManager instance = new StatesOfCapitalsQuizManager();
    
    public static StatesOfCapitalsQuizManager getInstance() {
        return instance;
    }
    
    @Override
    QuestionData createQuestionData(State state, RandomnessService randomnessService) {
        var question = state.getCapital() + " is the capital of which state?";
        int correctIndex;
        
        boolean noneOfTheAboveIsCorrectAnswer =
            randomnessService.thisHasApproximatelyAOneInFiveChanceOfReturningTrue();
        int numRandomStatesToGet;
        
        if (noneOfTheAboveIsCorrectAnswer) {
            numRandomStatesToGet = 4;
            correctIndex = 4;
        } else {
            numRandomStatesToGet = 3;
            correctIndex = randomnessService.random.nextInt(4);
        }
        
        List<String> choices =
            randomnessService
            .getStreamOfRandomStates()
            .filter(s -> !s.getId().equals(state.getId()))
            .limit(numRandomStatesToGet)
            .map(State::getName)
            .collect(Collectors.toCollection(ArrayList::new));
        
        if (!noneOfTheAboveIsCorrectAnswer) {
            choices.add(correctIndex, state.getName());
        }
        
        return new QuestionData(question, choices, correctIndex);
    }
}