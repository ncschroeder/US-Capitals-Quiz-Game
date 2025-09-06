package uscapitalsquizgame.scoredata;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ScoreDataRepository extends CrudRepository<ScoreData, Integer> {
    List<ScoreData> findByIsForCapitalsOfStatesQuiz(boolean isForCapitalsOfStatesQuiz);
    
    List<ScoreData> findByIsForCapitalsOfStatesQuizOrderByScoreDescDateTimeAsc(boolean isForCapitalsOfStatesQuiz);
}