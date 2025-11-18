package com.uscapitalsquizgame;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudyViewController {
    @GetMapping("/study")
    public String study() {
        return "study.html";
    }
}