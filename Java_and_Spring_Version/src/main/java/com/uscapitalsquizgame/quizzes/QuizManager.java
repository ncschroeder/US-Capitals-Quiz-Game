package com.uscapitalsquizgame.quizzes;

import java.util.List;
import com.uscapitalsquizgame.RandomnessService;
import com.uscapitalsquizgame.states.State;

public abstract sealed class QuizManager
    permits CapitalsOfStatesQuizManager, StatesOfCapitalsQuizManager {
    
    public static final int NUM_QUESTIONS = 10;
    
    private final String lowercaseQuizTypeString;
    
    private final String quizUrl;
    
    /**
     * This'll be used in a link to the quiz URL.
     */
    private final String takeAnotherQuizText;
    
    /**
     * The text to use for the title and heading HTML elements on a quiz page.
     */
    private final String quizTitleAndHeading;
    
    private final String addScoreUrl;
    
    private final String seeScoresUrl;
    
    private final String seeScoresUrlText;
    
    /**
     * The text to use for the title and heading HTML elements on a scores page.
     */
    private final String scoresTitleAndHeading;
    
    /**
     * The args for quizTypeStringWithUppercase are are "Capitals of States" and
     * "States of Capitals."
     */
    protected QuizManager(String quizTypeStringWithUppercase) {
        lowercaseQuizTypeString = quizTypeStringWithUppercase.toLowerCase();
        quizTitleAndHeading = quizTypeStringWithUppercase + " Quiz";
        scoresTitleAndHeading = quizTitleAndHeading + " Scores";
        
        quizUrl = lowercaseQuizTypeString.replace(' ', '-') + "-quiz";
        addScoreUrl = quizUrl + "-score";
        seeScoresUrl = addScoreUrl + 's';
        
        takeAnotherQuizText = "Take another quiz over " + lowercaseQuizTypeString;
        seeScoresUrlText = String.format("See scores for %s quizzes", lowercaseQuizTypeString);
    }
    
    abstract QuestionData createQuestionData(State state, RandomnessService randomnessService);
    
    final List<QuestionData> createQuestionDataListForQuiz(RandomnessService randomnessService) {
        return
            randomnessService
            .getStreamOfRandomStates()
            .limit(NUM_QUESTIONS)
            .map(state -> createQuestionData(state, randomnessService))
            .toList();
    }
    
    final String getQuizTitleAndHeading() {
        return quizTitleAndHeading;
    }
    
    final String getQuizUrl() {
        return quizUrl;
    }
    
    final String getTakeAnotherQuizText() {
        return takeAnotherQuizText;
    }
    
    /**
     * The value to use for the quiz_type column of the score_data table in the database.
     */
    public final String getQuizTypeDbColValue() {
        return lowercaseQuizTypeString;
    }
    
    final String getAddScoreUrl() {
        return addScoreUrl;
    }
    
    final String getSeeScoresUrl() {
        return seeScoresUrl;
    }
    
    final String getSeeScoresUrlText() {
        return seeScoresUrlText;
    }
    
    public final String getScoresTitleAndHeading() {
        return scoresTitleAndHeading;
    }
}