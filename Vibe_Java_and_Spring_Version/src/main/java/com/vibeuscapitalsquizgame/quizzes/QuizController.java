package com.vibeuscapitalsquizgame.quizzes;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import com.vibeuscapitalsquizgame.scores.ScoreController;
import com.vibeuscapitalsquizgame.states.State;
import com.vibeuscapitalsquizgame.states.StateService;

@Controller
public class QuizController {
    public static final int QUESTION_COUNT = 10;
    private static final int NUM_CHOICES = 5;
    private static final String NONE_OF_THE_ABOVE = "None of the above";

    private final StateService stateService;
    private final ObjectMapper objectMapper;
    private final Random random = new Random();

    public QuizController(StateService stateService, ObjectMapper objectMapper) {
        this.stateService = stateService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/quiz/capitals")
    public String capitalsQuiz(Model model) throws JacksonException {
        return renderQuiz(
            model,
            "Quiz over capitals of states",
            ScoreController.CAPITALS_ENDPOINT,
            this::generateCapitalsQuestion,
            QuizType.CAPITALS
        );
    }

    @GetMapping("/quiz/states")
    public String statesQuiz(Model model) throws JacksonException {
        return renderQuiz(
            model,
            "Quiz over states of capitals",
            ScoreController.STATES_ENDPOINT,
            this::generateStatesQuestion,
            QuizType.STATES
        );
    }

    private String renderQuiz(
        Model model,
        String heading,
        String scoresEndpoint,
        BiFunction<State, List<State>, Question> generator,
        QuizType quizType
    ) throws JacksonException {
        List<State> allStates = stateService.getStates();
        List<Question> questions =
            randomStates(allStates, QUESTION_COUNT, null)
            .map(state -> generator.apply(state, allStates))
            .toList();

        model.addAllAttributes(
            Map.of(
                "heading", heading,
                "minNameLength", ScoreController.MIN_NAME_LENGTH,
                "maxNameLength", ScoreController.MAX_NAME_LENGTH,
                "scoresEndpoint", scoresEndpoint,
                "questionsJson", objectMapper.writeValueAsString(questions),
                "quizType", quizType.name()
            )
        );
        
        return "quiz";
    }

    private Question generateCapitalsQuestion(State state, List<State> allStates) {
        String prompt = String.format("What's the capital of %s?", state.getName());
        boolean noneOfTheAboveIsCorrect = random.nextInt(NUM_CHOICES) == 0;
        var choices = new ArrayList<String>(NUM_CHOICES);
        List<String> paramStateCities = state.getCities();
        String paramStateCapital = state.getCapital();
        String randomParamStateCity1 = pickRandomCity(state);
        choices.add(randomParamStateCity1);
        
        String answer;
        if (noneOfTheAboveIsCorrect) {
            String randomParamStateCity2;
            do {
                randomParamStateCity2 = pickRandomCity(state);
            } while (randomParamStateCity2.equals(randomParamStateCity1));
            choices.add(randomParamStateCity2);
            answer = NONE_OF_THE_ABOVE;
        } else {
            choices.add(paramStateCapital);
            answer = paramStateCapital;
        }
        
        Supplier<State> getRandomState = () -> allStates.get(random.nextInt(allStates.size()));
        
        String randomCityFromAnotherState;
        do {
            randomCityFromAnotherState = pickRandomCity(getRandomState.get());
        } while (
            randomCityFromAnotherState.equals(paramStateCapital) ||
            paramStateCities.contains(randomCityFromAnotherState)
        );
        choices.add(randomCityFromAnotherState);
        
        String otherStateCapital;
        do {
            otherStateCapital = getRandomState.get().getCapital();
        } while (
            otherStateCapital.equals(paramStateCapital) ||
            otherStateCapital.equals(randomCityFromAnotherState) ||
            paramStateCities.contains(otherStateCapital)
        );
        choices.add(otherStateCapital);
        
        Collections.shuffle(choices);
        choices.add(NONE_OF_THE_ABOVE);
        return new Question(prompt, choices, answer);
    }

    private Question generateStatesQuestion(State state, List<State> allStates) {
        String prompt = String.format("%s is the capital of which state?", state.getCapital());
        boolean noneOfTheAboveIsCorrect = random.nextInt(NUM_CHOICES) == 0;
        
        int numRandomStatesToGet;
        String answer;
        if (noneOfTheAboveIsCorrect) {
            numRandomStatesToGet = NUM_CHOICES - 1;
            answer = NONE_OF_THE_ABOVE;
        } else {
            numRandomStatesToGet = NUM_CHOICES - 2;
            answer = state.getName();
        }
        
        var choices = new ArrayList<String>(NUM_CHOICES);
        randomStates(allStates, numRandomStatesToGet, state).forEachOrdered(s -> choices.add(s.getName()));
        if (!noneOfTheAboveIsCorrect) {
            choices.add(random.nextInt(choices.size() + 1), state.getName());
        }
        choices.add(NONE_OF_THE_ABOVE);
        return new Question(prompt, choices, answer);
    }

    private String pickRandomCity(State state) {
        List<String> cities = state.getCities();
        return cities.get(random.nextInt(cities.size()));
    }

    private Stream<State> randomStates(List<State> states, int count, State exclude) {
        return
            random.ints(0, states.size())
            .distinct()
            .mapToObj(states::get)
            .filter(s -> s != exclude)
            .limit(count);
    }
}