package com.uscapitalsquizgame.quizzes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuizManagerConfig {
    @Bean
    public CapitalsOfStatesQuizManager capitalsOfStatesQuizManager() {
        return new CapitalsOfStatesQuizManager();
    }
    
    @Bean
    public StatesOfCapitalsQuizManager statesOfCapitalsQuizManager() {
        return new StatesOfCapitalsQuizManager();
    }
}