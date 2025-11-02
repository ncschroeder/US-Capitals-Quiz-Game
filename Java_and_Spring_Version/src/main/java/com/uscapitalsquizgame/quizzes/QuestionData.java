package com.uscapitalsquizgame.quizzes;

import java.util.List;

record QuestionData(String question, List<String> choices, int correctIndex) {}