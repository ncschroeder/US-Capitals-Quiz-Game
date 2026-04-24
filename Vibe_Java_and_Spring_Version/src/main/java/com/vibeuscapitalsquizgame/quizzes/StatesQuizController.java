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
public class StatesQuizController {
    private static final int QUESTION_COUNT = 10;
    private static final int NONE_CORRECT_ODDS = 5;
    private static final int NON_NOTA_DISTRACTORS = 3;
    private static final int NOTA_DISTRACTORS = 4;
    private static final String NONE_OF_THE_ABOVE = "None of the above";

    private final StateService stateService;
    private final ObjectMapper objectMapper;
    private final Random random = new Random();

    public StatesQuizController(StateService stateService, ObjectMapper objectMapper) {
        this.stateService = stateService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/quiz/states")
    public String quiz(Model model) throws JsonProcessingException {
        List<Question> questions = generateQuestions();
        model.addAttribute("questionsJson", objectMapper.writeValueAsString(questions));
        model.addAttribute("heading", "Quiz over states of capitals");
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
        String prompt = String.format("%s is the capital of which state?", state.getCapital());
        boolean noneCorrect = random.nextInt(NONE_CORRECT_ODDS) == 0;

        if (noneCorrect) {
            List<String> choices = new ArrayList<>(pickOtherStateNames(state, allStates, NOTA_DISTRACTORS));
            Collections.shuffle(choices);
            choices.add(NONE_OF_THE_ABOVE);
            return new Question(prompt, choices, NONE_OF_THE_ABOVE);
        }

        List<String> choices = new ArrayList<>();
        choices.add(state.getName());
        choices.addAll(pickOtherStateNames(state, allStates, NON_NOTA_DISTRACTORS));
        Collections.shuffle(choices);
        return new Question(prompt, choices, state.getName());
    }

    private List<String> pickOtherStateNames(State exclude, List<State> allStates, int count) {
        List<State> candidates = new ArrayList<>(allStates);
        candidates.remove(exclude);
        Collections.shuffle(candidates);
        return candidates.subList(0, count).stream()
            .map(State::getName)
            .toList();
    }
}