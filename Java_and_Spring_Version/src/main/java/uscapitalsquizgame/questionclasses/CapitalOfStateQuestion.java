package uscapitalsquizgame.questionclasses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import uscapitalsquizgame.RandomnessService;
import uscapitalsquizgame.statetypes.NonEntityState;

public final class CapitalOfStateQuestion extends Question {
    public final String state;
    
    public CapitalOfStateQuestion(NonEntityState state, RandomnessService randomnessService) {
        this.state = state.getName();
        String paramStateCapital = state.getCapital();
        choices = new ArrayList<>(4);
        List<String> paramStateCities = state.getCities();
        String randomParamStateCity1 = randomnessService.getRandomElement(paramStateCities);
        choices.add(randomParamStateCity1);
        
        boolean useNoneOfTheAbove = randomnessService.thisHasApproximatelyAOneInFiveChanceOfReturningTrue();
        if (useNoneOfTheAbove) {
            correctIndex = 4;
            
            String randomParamStateCity2;
            do {
                randomParamStateCity2 = randomnessService.getRandomElement(paramStateCities);
            } while (randomParamStateCity2 == randomParamStateCity1);
            choices.add(randomParamStateCity2);
        } else {
            choices.add(paramStateCapital);
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
        if (!useNoneOfTheAbove) {
            correctIndex = choices.indexOf(paramStateCapital);
        }
    }
    
    @Override
    public String toString() {
        return String.format("CapitalOfStateQuestion[state=%s, choices=%s, correctIndex=%d", state, choices, correctIndex);
    }
}
