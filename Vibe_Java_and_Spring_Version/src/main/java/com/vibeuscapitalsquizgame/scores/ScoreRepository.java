package com.vibeuscapitalsquizgame.scores;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.vibeuscapitalsquizgame.quizzes.QuizType;

public interface ScoreRepository extends JpaRepository<Score, Integer> {
    List<Score> findByQuizTypeOrderByScoreDescSubmittedAtAsc(QuizType quizType);
}