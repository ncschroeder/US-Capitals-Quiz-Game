package uscapitalsquizgame.questions;

import java.util.ArrayList;
import java.util.stream.Collectors;
import uscapitalsquizgame.RandomnessService;
import uscapitalsquizgame.states.State;

public final class StatesOfCapitalsQuestion extends Question {
    private final String capital;
    
    public StatesOfCapitalsQuestion(State state, RandomnessService randomnessService) {
        capital = state.getCapital();
        
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
        
        choices =
            randomnessService
            .getStreamOfRandomStates(numRandomStatesToGet)
            .map(State::getName)
            .collect(Collectors.toCollection(ArrayList::new));
        
        if (!noneOfTheAboveIsCorrectAnswer) {
            choices.add(correctIndex, state.getName());
        }
    }
    
    @Override
    public String toString() {
        return String.format(
            "StateOfCapitalQuestion[capital=%s, choices=%s, correctIndex=%d]",
            capital, choices, correctIndex
        );
    }
    
    public String getCapital() {
        return capital;
    }
}