package com.uscapitalsquizgame.questions;

import java.util.*;
import com.uscapitalsquizgame.RandomnessService;
import com.uscapitalsquizgame.states.State;

public final class CapitalsOfStatesQuestion extends Question {
    private final String stateName;
    
    public CapitalsOfStatesQuestion(State state, RandomnessService randomnessService) {
        stateName = state.getName();
        String paramStateCapital = state.getCapital();
        choices = new ArrayList<>(4);
        List<String> paramStateCities = state.getCities();
        String randomParamStateCity1 = randomnessService.getRandomElement(paramStateCities);
        choices.add(randomParamStateCity1);
        
        boolean noneOfTheAboveIsCorrectAnswer =
            randomnessService.thisHasApproximatelyAOneInFiveChanceOfReturningTrue();
        if (!noneOfTheAboveIsCorrectAnswer) {
            choices.add(paramStateCapital);
        } else {
            correctIndex = 4;
            
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
    }
    
    @Override
    public String toString() {
        return String.format(
            "CapitalOfStateQuestion[stateName=%s, choices=%s, correctIndex=%d]",
            stateName, choices, correctIndex
        );
    }
    
    public String getStateName() {
        return stateName;
    }
}