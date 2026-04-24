package com.vibeuscapitalsquizgame.quizzes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vibeuscapitalsquizgame.states.State;
import com.vibeuscapitalsquizgame.states.StateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class CapitalsQuizController {
    private static final int QUESTION_COUNT = 10;
    private static final int NONE_CORRECT_ODDS = 5;
    private static final String NONE_OF_THE_ABOVE = "None of the above";

    private final StateService stateService;
    private final ObjectMapper objectMapper;
    private final Random random = new Random();

    public CapitalsQuizController(StateService stateService, ObjectMapper objectMapper) {
        this.stateService = stateService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/quiz/capitals")
    public String quiz(Model model) throws JsonProcessingException {
        List<Question> questions = generateQuestions();
        model.addAttribute("questionsJson", objectMapper.writeValueAsString(questions));
        model.addAttribute("heading", "Quiz over capitals of states");
        return "quiz";
    }

    private List<Question> generateQuestions() {
        List<State> all = stateService.getStates();
        List<State> shuffled = new ArrayList<>(all);
        Collections.shuffle(shuffled);
        return shuffled.subList(0, QUESTION_COUNT).stream()
            .map(state -> generateQuestion(state, all))
            .toList();
    }

    private Question generateQuestion(State state, List<State> allStates) {
        State otherState = pickOtherState(state, allStates);
        String prompt = String.format("What is the capital of %s?", state.getName());
        boolean noneCorrect = random.nextInt(NONE_CORRECT_ODDS) == 0;

        if (noneCorrect) {
            List<String> twoCities = pickTwoCities(state);
            List<String> choices = new ArrayList<>();
            choices.add(twoCities.get(0));
            choices.add(twoCities.get(1));
            choices.add(otherState.getCapital());
            choices.add(pickRandomCity(otherState));
            Collections.shuffle(choices);
            choices.add(NONE_OF_THE_ABOVE);
            return new Question(prompt, choices, NONE_OF_THE_ABOVE);
        }

        List<String> choices = new ArrayList<>();
        choices.add(state.getCapital());
        choices.add(pickRandomCity(state));
        choices.add(otherState.getCapital());
        choices.add(pickRandomCity(otherState));
        Collections.shuffle(choices);
        return new Question(prompt, choices, state.getCapital());
    }

    private State pickOtherState(State state, List<State> allStates) {
        State picked;
        do {
            picked = allStates.get(random.nextInt(allStates.size()));
        } while (picked == state);
        return picked;
    }

    private List<String> pickTwoCities(State state) {
        List<String> cities = new ArrayList<>(state.getCities());
        Collections.shuffle(cities);
        return cities.subList(0, 2);
    }

    private String pickRandomCity(State state) {
        List<String> cities = state.getCities();
        return cities.get(random.nextInt(cities.size()));
    }
}
