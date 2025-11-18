package com.uscapitalsquizgame.quizzes;

import java.util.*;
import com.uscapitalsquizgame.RandomnessService;
import com.uscapitalsquizgame.states.State;

public final class CapitalsOfStatesQuizManager extends QuizManager {
    public static final String QUIZ_URL_END = "/capitals-of-states-quiz";
    
    public static final String SCORE_URL_END = QUIZ_URL_END + "-score";
    
    public static final String SCORES_URL_END = SCORE_URL_END + 's';
    
    CapitalsOfStatesQuizManager() {
        super("Capitals of States", QUIZ_URL_END, SCORE_URL_END, SCORES_URL_END);
    }
    
    @Override
    QuestionData createQuestionData(State state, RandomnessService randomnessService) {
        var question = String.format("What's the capital of %s?", state.getName());
        // choices won't contain "None of the above."
        var choices = new ArrayList<String>(NUM_ANSWER_CHOICES - 1);
        
        /*
        This'll be reassigned but I need to initialize it since the compiler doesn't know
        that it'll be reassigned since that's done in different if-else statements.
         */
        int correctIndex = 0;
        
        String paramStateCapital = state.getCapital();
        List<String> paramStateCities = state.getCities();
        String randomParamStateCity1 = randomnessService.getRandomElement(paramStateCities);
        choices.add(randomParamStateCity1);
        
        boolean noneOfTheAboveIsCorrectAnswer =
            randomnessService.thisHasApproximatelyAOneInFiveChanceOfReturningTrue();
        
        if (!noneOfTheAboveIsCorrectAnswer) {
            choices.add(paramStateCapital);
        } else {
            correctIndex = NUM_ANSWER_CHOICES - 1;
            
            String randomParamStateCity2;
            do {
                randomParamStateCity2 = randomnessService.getRandomElement(paramStateCities);
            } while (randomParamStateCity2 == randomParamStateCity1);
            choices.add(randomParamStateCity2);
        }
        
        /*
        There are 2 duplicate cities:
        - Columbia, the capital of South Carolina and a populous city in central Missouri.
        - Portland, the most populous city, but not the capital, in Maine and Oregon.
        
        The conditions to check in the loops below ensure that there won't be duplicate choices.
         */
        
        String randomCityFromAnotherState;
        do {
            randomCityFromAnotherState = randomnessService.getRandomCity();
        } while (
            randomCityFromAnotherState.equals(paramStateCapital) ||
            paramStateCities.contains(randomCityFromAnotherState)
        );
        choices.add(randomCityFromAnotherState);
        
        String otherStateCapital;
        do {
            otherStateCapital = randomnessService.getRandomCapital();
        } while (
            otherStateCapital.equals(paramStateCapital) ||
            otherStateCapital.equals(randomCityFromAnotherState) ||
            paramStateCities.contains(otherStateCapital)
        );
        choices.add(otherStateCapital);
        
        Collections.shuffle(choices, randomnessService.random);
        if (!noneOfTheAboveIsCorrectAnswer) {
            correctIndex = choices.indexOf(paramStateCapital);
        }
        
        return new QuestionData(question, choices, correctIndex);
    }
}