package com.github.adhambadawi.minisurveymonkey.controller;

import com.github.adhambadawi.minisurveymonkey.model.Question;
import com.github.adhambadawi.minisurveymonkey.model.Survey;
import com.github.adhambadawi.minisurveymonkey.repository.QuestionRepository;
import com.github.adhambadawi.minisurveymonkey.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class ApplicationController {

    private final SurveyService surveyService;

    @Autowired
    public ApplicationController(SurveyService surveyService, QuestionRepository questionRepository) {
        this.surveyService = surveyService;
    }

    @GetMapping("/survey/new")
    public String showSurveyForm(Model model) {
        model.addAttribute("survey", new Survey());
        model.addAttribute("question", new Question());
        return "surveyForm";
    }

    @PostMapping("/survey/new")
    public String submitSurveyForm(@ModelAttribute Survey survey) throws Exception {
        //Remove any questions with input issues
        List<Question> validQuestions = survey.getQuestions().stream()
                .filter(question -> question != null && question.getText() != null && !question.getText().trim().isEmpty())
                .toList();

        survey.setQuestions(validQuestions);
        for (Question question : validQuestions) {
            question.setSurvey(survey);
        }
        surveyService.createSurvey(survey);
        return "surveyQuestions";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }


}
