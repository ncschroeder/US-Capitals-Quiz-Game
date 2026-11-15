package com.aiuscapitalsquizgame.scores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.aiuscapitalsquizgame.quizzes.QuizType;

public interface ScoreRepository extends JpaRepository<Score, Integer> {
    List<Score> findByQuizTypeOrderByScoreDescSubmittedAtAsc(QuizType quizType);

    @Transactional
    long deleteByQuizType(QuizType quizType);
}