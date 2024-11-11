package com.github.adhambadawi.minisurveymonkey.controller;

import com.github.adhambadawi.minisurveymonkey.model.Survey;
import com.github.adhambadawi.minisurveymonkey.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/results")
public class ResultController {

    private final SurveyRepository surveyRepository;

    @Autowired
    public ResultController(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @GetMapping("/{id}")
    public String getSurveyResultsBySurveyID(@PathVariable("id") long id, Model model) {
        Survey survey = surveyRepository.findById(id);
        model.addAttribute("survey", survey);
        return "result";
    }
}
