package com.uscapitalsquizgame.scoredata;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ScoreDataRepository extends CrudRepository<ScoreData, Integer> {
    List<ScoreData> findByQuizType(String quizType);
    
    List<ScoreData> findByQuizTypeOrderByScoreDescSaveDateTimeAsc(String quizType);
}