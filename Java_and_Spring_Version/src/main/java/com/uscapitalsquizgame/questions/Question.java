package com.uscapitalsquizgame.questions;

import java.util.List;

public abstract class Question {
    protected List<String> choices;
    
    protected int correctIndex;
    
    public List<String> getChoices() {
        return choices;
    }
    
    public int getCorrectIndex() {
        return correctIndex;
    }
}