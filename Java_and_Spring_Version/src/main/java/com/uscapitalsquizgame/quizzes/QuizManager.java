package com.uscapitalsquizgame.quizzes;

import java.util.List;
import com.uscapitalsquizgame.states.State;

import static com.uscapitalsquizgame.quizzes.QuizCreation.*;

public abstract sealed class QuizManager
    permits CapitalsOfStatesQuizManager, StatesOfCapitalsQuizManager {
    
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
     * The args for quizTypeStringWithUppercase are "Capitals of States" and "States of Capitals".
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
    
    abstract QuestionData createQuestionData(State state, boolean noneOfTheAboveIsCorrectAnswer);
    
    final List<QuestionData> createQuestionDataListForQuiz() {
        return
            getStreamOfRandomStates()
            .limit(NUM_QUESTIONS)
            .map(state -> {
                boolean noneOfTheAboveIsCorrectAnswer = random.nextInt(NUM_ANSWER_CHOICES) == 0;
                return createQuestionData(state, noneOfTheAboveIsCorrectAnswer);
            })
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