package uscapitalsquizgame.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ScoresController {
    @GetMapping("/capitalsOfStatesQuizScores")
    public String capitalsOfStatesQuizScores(Model model) {
        return "";
    }
    
    @GetMapping("/statesOfCapitalsQuizScores")
    public String statesOfCapitalsQuizScores(Model model) {
        return "";
    }
    
    @PostMapping("/addCapitalsOfStatesQuizScore")
    public void addCapitalsOfStatesQuizScore(@RequestParam int score, @RequestParam String name) {
    }
    
    @PostMapping("/addStatesOfCapitalsQuizScore")
    public void addStatesOfCapitalsQuizScore(@RequestParam int score, @RequestParam String name) {
    }
}
