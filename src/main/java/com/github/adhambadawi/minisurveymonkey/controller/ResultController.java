package com.github.adhambadawi.minisurveymonkey.controller;

import com.github.adhambadawi.minisurveymonkey.model.Survey;
import com.github.adhambadawi.minisurveymonkey.model.User;
import com.github.adhambadawi.minisurveymonkey.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/results")
public class ResultController {


    @GetMapping("/results/{id}")
    public String getSurveyResults(@PathVariable("id") int id, Model model) {
        model.addAttribute("survey", surveyService.getSurvey(id));
        return "result";
    }
}
