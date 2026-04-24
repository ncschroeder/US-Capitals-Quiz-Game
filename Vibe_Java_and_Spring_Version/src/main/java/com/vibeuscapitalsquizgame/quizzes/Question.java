package com.vibeuscapitalsquizgame.quizzes;

import java.util.List;

public record Question(String prompt, List<String> choices, String correctAnswer) {
}