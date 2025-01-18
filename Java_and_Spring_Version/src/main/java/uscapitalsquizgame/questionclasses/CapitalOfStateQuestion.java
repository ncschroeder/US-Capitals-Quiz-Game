package uscapitalsquizgame.questionclasses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import uscapitalsquizgame.entities.NonCapitalCity;
import uscapitalsquizgame.entities.State;
import uscapitalsquizgame.repositories.NonCapitalCitiesRepository;
import uscapitalsquizgame.repositories.StatesRepository;

public final class CapitalOfStateQuestion extends Question {
    public final String state;
    
    public CapitalOfStateQuestion(State state, StatesRepository statesRepository, NonCapitalCitiesRepository nonCapitalCitiesRepository) {
        this.state = state.getName();
        choices = new ArrayList<>(4);
        
        List<String> nccsInParamStateNames =
            nonCapitalCitiesRepository
            .findByStateId(state.getId())
            .map(NonCapitalCity::getName)
            .toList();
        
        String randomNccInParamState1Name = getRandomElement(nccsInParamStateNames);
        choices.add(randomNccInParamState1Name);
        
        boolean useNoneOfTheAbove = random.nextInt(5) == 0;
        if (useNoneOfTheAbove) {
            correctIndex = 4;
            
            String randomNccInParamState2Name;
            do {
                randomNccInParamState2Name = getRandomElement(nccsInParamStateNames);
            } while (randomNccInParamState2Name == randomNccInParamState1Name);
            choices.add(randomNccInParamState2Name);
        } else {
            choices.add(state.getCapital());
        }
        
        String randomNccFromOtherStateName;
        do {
            int randomId = random.nextInt(1, (int) nonCapitalCitiesRepository.count() + 1);
            randomNccFromOtherStateName = nonCapitalCitiesRepository.findById(randomId).get().getName();
        } while (
            randomNccFromOtherStateName.equals(state.getCapital()) ||
            nccsInParamStateNames.contains(randomNccFromOtherStateName)
        );
        choices.add(randomNccFromOtherStateName);
        
        String otherStateCapital;
        do {
            otherStateCapital = statesRepository.findById(getRandomStateId()).get().getCapital();
        } while (
            otherStateCapital.equals(state.getCapital()) ||
            otherStateCapital.equals(randomNccFromOtherStateName) ||
            nccsInParamStateNames.contains(otherStateCapital)
        );
        choices.add(otherStateCapital);
        
        Collections.shuffle(choices, random);
        if (!useNoneOfTheAbove) {
            correctIndex = choices.indexOf(state.getCapital());
        }
    }
    
    @Override
    public String toString() {
        return String.format("CapitalOfStateQuestion[state=%s, choices=%s, correctIndex=%d", state, choices, correctIndex);
    }
}
