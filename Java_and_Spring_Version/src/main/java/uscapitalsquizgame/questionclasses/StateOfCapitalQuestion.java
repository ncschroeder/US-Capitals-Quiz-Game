package uscapitalsquizgame.questionclasses;

import java.util.ArrayList;
import java.util.stream.Collectors;
import uscapitalsquizgame.RandomnessService;
import uscapitalsquizgame.statetypes.NonEntityState;

public final class StateOfCapitalQuestion extends Question {
    public final String capital;
    
    public StateOfCapitalQuestion(NonEntityState state, RandomnessService randomnessService) {
        capital = state.getCapital();
        
        boolean useNoneOfTheAbove = randomnessService.thisHasApproximatelyAOneInFiveChanceOfReturningTrue();
        int numRandomStatesToGet;
        
        if (useNoneOfTheAbove) {
            numRandomStatesToGet = 4;
            correctIndex = 4;
        } else {
            numRandomStatesToGet = 3;
            correctIndex = randomnessService.random.nextInt(4);
        }
        
        choices =
            randomnessService
            .getStreamOfRandomStates(numRandomStatesToGet)
            .map(NonEntityState::getName)
            .collect(Collectors.toCollection(ArrayList::new));
        
        if (!useNoneOfTheAbove) {
            choices.add(correctIndex, state.getName());
        }
    }
}
