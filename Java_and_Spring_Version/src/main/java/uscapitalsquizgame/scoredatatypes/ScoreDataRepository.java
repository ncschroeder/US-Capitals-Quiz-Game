package uscapitalsquizgame.scoredatatypes;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ScoreDataRepository extends CrudRepository<ScoreData, Integer> {
    List<ScoreData> findAllByIsForCapitalsOfStatesQuizOrderByScoreDescDateTimeAsc(boolean isForCapitalsOfStatesQuiz);
}
