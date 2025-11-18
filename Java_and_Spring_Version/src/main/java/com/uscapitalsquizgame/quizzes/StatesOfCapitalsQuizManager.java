package com.uscapitalsquizgame.quizzes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.uscapitalsquizgame.RandomnessService;
import com.uscapitalsquizgame.states.State;

public final class StatesOfCapitalsQuizManager extends QuizManager {
    public static final String QUIZ_URL_END = "/states-of-capitals-quiz";
    
    public static final String SCORE_URL_END = QUIZ_URL_END + "-score";
    
    public static final String SCORES_URL_END = SCORE_URL_END + 's';
    
    StatesOfCapitalsQuizManager() {
        super("States of Capitals", QUIZ_URL_END, SCORE_URL_END, SCORES_URL_END);
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