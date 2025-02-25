package uscapitalsquizgame.questionclasses;

import java.util.ArrayList;
import java.util.stream.Collectors;
import uscapitalsquizgame.statetypes.NonEntityState;

public final class StateOfCapitalQuestion extends Question {
    public final String capital;
    
        capital = state.getCapital();
        
        boolean useNoneOfTheAbove = random.nextInt(5) == 0;
        int numRandomStatesToGet;
        
        if (useNoneOfTheAbove) {
            numRandomStatesToGet = 4;
            correctIndex = 4;
        } else {
            numRandomStatesToGet = 3;
            correctIndex = random.nextInt(4);
        }
        
        choices =
            getStreamOfRandomStates(statesRepository, numRandomStatesToGet)
            .map(State::getName)
            .collect(Collectors.toCollection(ArrayList::new));
        
        if (!useNoneOfTheAbove) {
            choices.add(correctIndex, state.getName());
        }
    }
}
