package com.uscapitalsquizgame.quizzes;

import java.util.*;
import com.uscapitalsquizgame.states.State;

import static com.uscapitalsquizgame.quizzes.QuizCreation.*;

public final class CapitalsOfStatesQuizManager extends QuizManager {
    public static final String QUIZ_URL_END = "/capitals-of-states-quiz";
    public static final String SCORE_URL_END = QUIZ_URL_END + "-score";
    public static final String SCORES_URL_END = SCORE_URL_END + 's';
    
    CapitalsOfStatesQuizManager() {
        super("Capitals of States", QUIZ_URL_END, SCORE_URL_END, SCORES_URL_END);
    }
    
    @Override
    QuestionData createQuestionData(State state, boolean noneOfTheAboveIsCorrectAnswer) {
        var question = String.format("What's the capital of %s?", state.getName());
        
        // This won't contain "None of the above".
        var answerChoices = new ArrayList<String>(NUM_ANSWER_CHOICES - 1);
        
        /*
        This'll be reassigned but I need to initialize it since the compiler doesn't know
        that it'll be reassigned since that's done in different if-else statements.
         */
        int correctIndex = 0;
        
        String paramStateCapital = state.getCapital();
        List<String> paramStateCities = state.getCities();
        String randomParamStateCity1 = getRandomElement(paramStateCities);
        answerChoices.add(randomParamStateCity1);
        
        if (!noneOfTheAboveIsCorrectAnswer) {
            answerChoices.add(paramStateCapital);
        } else {
            correctIndex = NUM_ANSWER_CHOICES - 1;
            
            String randomParamStateCity2;
            do {
                randomParamStateCity2 = getRandomElement(paramStateCities);
            } while (randomParamStateCity2 == randomParamStateCity1);
            answerChoices.add(randomParamStateCity2);
        }
        
        /*
        There are 2 duplicate cities:
        - Columbia, the capital of South Carolina and a populous city in central Missouri.
        - Portland, the most populous city, but not the capital, in Maine and Oregon.
        
        The conditions to check in the loops below ensure that there won't be duplicate choices.
         */
        
        String randomCityFromAnotherState;
        do {
            randomCityFromAnotherState = getRandomCity();
        } while (
            randomCityFromAnotherState.equals(paramStateCapital) ||
            paramStateCities.contains(randomCityFromAnotherState)
        );
        answerChoices.add(randomCityFromAnotherState);
        
        String otherStateCapital;
        do {
            otherStateCapital = getRandomCapital();
        } while (
            otherStateCapital.equals(paramStateCapital) ||
            otherStateCapital.equals(randomCityFromAnotherState) ||
            paramStateCities.contains(otherStateCapital)
        );
        answerChoices.add(otherStateCapital);
        
        Collections.shuffle(answerChoices, random);
        if (!noneOfTheAboveIsCorrectAnswer) {
            correctIndex = answerChoices.indexOf(paramStateCapital);
        }
        
        return new QuestionData(question, answerChoices, correctIndex);
    }
}