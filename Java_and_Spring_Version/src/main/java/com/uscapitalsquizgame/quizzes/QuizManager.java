package com.uscapitalsquizgame.quizzes;

import java.util.List;
import com.uscapitalsquizgame.RandomnessService;
import com.uscapitalsquizgame.states.State;

public abstract sealed class QuizManager
    permits CapitalsOfStatesQuizManager, StatesOfCapitalsQuizManager {
    
    public static final int NUM_QUESTIONS = 10;
    
    /**
     * The number of answer choices including "None of the above."
     */
    static final int NUM_ANSWER_CHOICES = 5;
    
    
    private final String lowercaseQuizTypeString;
    
    private final String quizUrlEnd;
    
    /**
     * The text to use for the title and heading HTML elements on a quiz page.
     */
    private final String quizTitleAndHeading;
    
    private final String addScoreUrlEnd;
    
    private final String takeAnotherQuizText;
    
    private final String seeScoresText;
    
    private final String seeScoresUrlEnd;
    
    /**
     * The text to use for the title and heading HTML elements on a scores page.
     */
    private final String scoresTitleAndHeading;
    
    /**
     * The args for quizTypeStringWithUppercase are "Capitals of States" and
     * "States of Capitals."
     */
    protected QuizManager(
        String quizTypeStringWithUppercase,
        String quizUrlEnd,
        String addScoreUrlEnd,
        String seeScoresUrlEnd
    ) {
        this.quizUrlEnd = quizUrlEnd;
        this.addScoreUrlEnd = addScoreUrlEnd;
        this.seeScoresUrlEnd = seeScoresUrlEnd;
        
        quizTitleAndHeading = quizTypeStringWithUppercase + " Quiz";
        scoresTitleAndHeading = quizTitleAndHeading + " Scores";
        
        lowercaseQuizTypeString = quizTypeStringWithUppercase.toLowerCase();
        takeAnotherQuizText = "Take another quiz over " + lowercaseQuizTypeString;
        seeScoresText = String.format("See scores for %s quizzes", lowercaseQuizTypeString);
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
    
    final String getQuizUrlEnd() {
        return quizUrlEnd;
    }
    
    final String getQuizTitleAndHeading() {
        return quizTitleAndHeading;
    }
    
    final String getAddScoreUrlEnd() {
        return addScoreUrlEnd;
    }
    
    final String getTakeAnotherQuizText() {
        return takeAnotherQuizText;
    }
    
    final String getSeeScoresText() {
        return seeScoresText;
    }
    
    final String getSeeScoresUrlEnd() {
        return seeScoresUrlEnd;
    }
    
    public final String getScoresTitleAndHeading() {
        return scoresTitleAndHeading;
    }
    
    /**
     * The value to use for the quiz_type column of the score_data table in the database.
     */
    public final String getQuizTypeDbColValue() {
        return lowercaseQuizTypeString;
    }
}