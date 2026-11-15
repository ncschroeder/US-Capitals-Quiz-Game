package com.aiuscapitalsquizgame.quizzes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import com.aiuscapitalsquizgame.scores.ScoreController;
import com.aiuscapitalsquizgame.states.State;
import com.aiuscapitalsquizgame.states.StateService;

@Controller
public class QuizController {
    public static final String CAPITALS_ENDPOINT = "/quiz/capitals";
    public static final String STATES_ENDPOINT = "/quiz/states";
    
    public static final int QUESTION_COUNT = 10;
    private static final int NUM_CHOICES = 5;
    private static final String NONE_OF_THE_ABOVE = "None of the above";

    @Autowired
    private StateService stateService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private final Random random = new Random();

    @GetMapping(CAPITALS_ENDPOINT)
    public String capitalsQuiz(Model model) throws JacksonException {
        return renderQuiz(
            model,
            "Quiz over capitals of states",
            "capitals of states",
            CAPITALS_ENDPOINT,
            ScoreController.CAPITALS_ENDPOINT,
            this::generateCapitalsQuestion,
            QuizType.CAPITALS
        );
    }

    @GetMapping(STATES_ENDPOINT)
    public String statesQuiz(Model model) throws JacksonException {
        return renderQuiz(
            model,
            "Quiz over states of capitals",
            "states of capitals",
            STATES_ENDPOINT,
            ScoreController.STATES_ENDPOINT,
            this::generateStatesQuestion,
            QuizType.STATES
        );
    }

    private String renderQuiz(
        Model model,
        String heading,
        String quizTypeLabel,
        String quizEndpoint,
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
                "quizTypeLabel", quizTypeLabel,
                "quizEndpoint", quizEndpoint,
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
            random
            .ints(0, states.size())
            .distinct()
            .mapToObj(states::get)
            .filter(s -> s != exclude)
            .limit(count);
    }
}