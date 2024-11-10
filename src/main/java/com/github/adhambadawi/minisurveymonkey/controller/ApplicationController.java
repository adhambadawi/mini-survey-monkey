package com.github.adhambadawi.minisurveymonkey.controller;

import com.github.adhambadawi.minisurveymonkey.model.Question;
import com.github.adhambadawi.minisurveymonkey.model.Survey;
import com.github.adhambadawi.minisurveymonkey.model.User;
import com.github.adhambadawi.minisurveymonkey.repository.QuestionRepository;
import com.github.adhambadawi.minisurveymonkey.service.SurveyService;
import com.github.adhambadawi.minisurveymonkey.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Controller
public class ApplicationController {

    private final SurveyService surveyService;
    private final UserService userService;

    @Autowired
    public ApplicationController(SurveyService surveyService, UserService userService) {
        this.surveyService = surveyService;
        this.userService = userService;
    }

    @GetMapping("/survey/new")
    public String showSurveyForm(Model model, Principal principal) {
        model.addAttribute("survey", new Survey());
        model.addAttribute("question", new Question());
        return "surveyForm";
    }

    @PostMapping("/survey/new")
    public String submitSurveyForm(@ModelAttribute Survey survey, Principal principal) throws Exception {
        // Get the logged-in user
        User creator = userService.findByUsername(principal.getName());
        survey.setCreator(creator);

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
    public String index(Model model, Principal principal) {
        List<Survey> surveys = surveyService.getAllSurveys();
        model.addAttribute("surveys", surveys);
        model.addAttribute("user", principal != null ? principal.getName() : null);
        return "index";
    }

    @GetMapping("/survey/fill")
    public String fillSurvey() {
        return "fillSurvey";
    }
  
    @PostMapping("/survey/{id}/close")
    public String closeSurvey(@PathVariable Long id, Principal principal) {
        Optional<Survey> optionalSurvey = surveyService.getSurveyById(id);
        if (optionalSurvey.isPresent()) {
            Survey survey = optionalSurvey.get();
            // Check if the logged-in user is the creator
            if (survey.getCreator().getUsername().equals(principal.getName())) {
                survey.setClosed(true);
                surveyService.updateSurvey(survey);
                return "redirect:/survey/" + id + "/details";
            } else {
                // Unauthorized access
                return "error/403";
            }
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/survey/{id}/details")
    public String surveyDetails(@PathVariable Long id, Model model) {
        Optional<Survey> optionalSurvey = surveyService.getSurveyById(id);
        if (optionalSurvey.isPresent()) {
            Survey survey = optionalSurvey.get();
            model.addAttribute("survey", survey);
            return "surveyDetails";
        } else {
            return "redirect:/";
        }
    }
}
