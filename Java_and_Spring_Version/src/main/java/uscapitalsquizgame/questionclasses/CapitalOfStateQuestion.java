package uscapitalsquizgame.questionclasses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import uscapitalsquizgame.statetypes.NonEntityState;

public final class CapitalOfStateQuestion extends Question {
    public final String state;
    
        this.state = state.getName();
        String paramStateCapital = state.getCapital();
        choices = new ArrayList<>(4);
        List<String> paramStateCities = state.getCities();
        
        
        boolean useNoneOfTheAbove = random.nextInt(5) == 0;
        if (useNoneOfTheAbove) {
            correctIndex = 4;
            
            String randomParamStateCity2;
            do {
            } while (randomParamStateCity2 == randomParamStateCity1);
            choices.add(randomParamStateCity2);
        } else {
            choices.add(paramStateCapital);
        }
        
        String randomCityFromOtherState;
        do {
        } while (
            randomCityFromOtherState.equals(paramStateCapital) ||
            paramStateCities.contains(randomCityFromOtherState)
        );
        choices.add(randomCityFromOtherState);
        
        String otherStateCapital;
        do {
        } while (
            otherStateCapital.equals(paramStateCapital) ||
            otherStateCapital.equals(randomCityFromOtherState) ||
            paramStateCities.contains(otherStateCapital)
        );
        choices.add(otherStateCapital);
        
        Collections.shuffle(choices, random);
        if (!useNoneOfTheAbove) {
            correctIndex = choices.indexOf(paramStateCapital);
        }
    }
    
    @Override
    public String toString() {
        return String.format("CapitalOfStateQuestion[state=%s, choices=%s, correctIndex=%d", state, choices, correctIndex);
    }
}
