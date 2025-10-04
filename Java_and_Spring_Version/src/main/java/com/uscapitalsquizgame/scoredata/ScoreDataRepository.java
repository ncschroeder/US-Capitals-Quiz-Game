package com.uscapitalsquizgame.scoredata;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ScoreDataRepository extends CrudRepository<ScoreData, Integer> {
    List<ScoreData> findByIsForCapitalsOfStatesQuiz(boolean isForCapitalsOfStatesQuiz);
    
    List<ScoreData> findByIsForCapitalsOfStatesQuizOrderByScoreDescDateTimeAsc(boolean isForCapitalsOfStatesQuiz);
}

//    int deleteByName(String name);
//    int deleteByIsForCapitalsOfStatesQuiz(boolean isForCapitalsOfStatesQuiz);
//    @Modifying
//    @Query("delete from ScoreData where isForCapitalsOfStatesQuiz = ?1")
//    int delete(boolean b);