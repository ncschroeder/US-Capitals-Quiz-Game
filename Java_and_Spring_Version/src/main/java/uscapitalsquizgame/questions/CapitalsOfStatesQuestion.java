package uscapitalsquizgame.questions;

import java.util.*;
import uscapitalsquizgame.RandomnessService;
import uscapitalsquizgame.states.State;

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
        
        String randomCityFromOtherState;
        do {
            randomCityFromOtherState = randomnessService.getRandomCity();
        } while (
            randomCityFromOtherState.equals(paramStateCapital) ||
            paramStateCities.contains(randomCityFromOtherState)
        );
        choices.add(randomCityFromOtherState);
        
        String otherStateCapital;
        do {
            otherStateCapital = randomnessService.getRandomCapital();
        } while (
            otherStateCapital.equals(paramStateCapital) ||
            otherStateCapital.equals(randomCityFromOtherState) ||
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