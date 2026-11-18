package com.uscapitalsquizgame.quizzes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.uscapitalsquizgame.states.State;

import static com.uscapitalsquizgame.quizzes.QuizCreation.*;

public final class StatesOfCapitalsQuizManager extends QuizManager {
    public static final String QUIZ_URL_END = "/states-of-capitals-quiz";
    public static final String SCORE_URL_END = QUIZ_URL_END + "-score";
    public static final String SCORES_URL_END = SCORE_URL_END + 's';
    
    StatesOfCapitalsQuizManager() {
        super("States of Capitals", QUIZ_URL_END, SCORE_URL_END, SCORES_URL_END);
    }
    
    @Override
    QuestionData createQuestionData(State state, boolean noneOfTheAboveIsCorrectAnswer) {
        var question = state.getCapital() + " is the capital of which state?";
        int numRandomStatesToGet;
        int correctIndex;
        
        if (noneOfTheAboveIsCorrectAnswer) {
            numRandomStatesToGet = correctIndex = NUM_ANSWER_CHOICES - 1;
        } else {
            numRandomStatesToGet = NUM_ANSWER_CHOICES - 2;
            correctIndex = random.nextInt(NUM_ANSWER_CHOICES - 1);
        }
        
        List<String> answerChoices =
            getStreamOfRandomStates()
            .filter(s -> s != state)
            .limit(numRandomStatesToGet)
            .map(State::getName)
            .collect(Collectors.toCollection(ArrayList::new));
        
        if (!noneOfTheAboveIsCorrectAnswer) {
            answerChoices.add(correctIndex, state.getName());
        }
        
        return new QuestionData(question, answerChoices, correctIndex);
    }
}