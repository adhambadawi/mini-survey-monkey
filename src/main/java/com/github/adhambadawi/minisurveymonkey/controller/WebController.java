package com.github.adhambadawi.minisurveymonkey.controller;

import com.github.adhambadawi.minisurveymonkey.model.Question;
import com.github.adhambadawi.minisurveymonkey.model.Survey;
import com.github.adhambadawi.minisurveymonkey.model.User;
import com.github.adhambadawi.minisurveymonkey.service.SurveyService;
import com.github.adhambadawi.minisurveymonkey.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class WebController {

    private final SurveyService surveyService;
    private final UserService userService;

    @Autowired
    public WebController(SurveyService surveyService, UserService userService) {
        this.surveyService = surveyService;
        this.userService = userService;
    }

    // Home page showing available surveys
    @GetMapping("/")
    public String index(Model model) {
        List<Survey> surveys = surveyService.getAllSurveys();
        model.addAttribute("surveys", surveys);
        return "index";
    }

    // Show form to create a new survey
    @GetMapping("/survey/new")
    public String showSurveyForm(Model model) {
        model.addAttribute("survey", new Survey());
        model.addAttribute("question", new Question());
        return "surveyForm"; // Ensure this matches your template name
    }

    // Handle form submission to create a new survey
    @PostMapping("/survey/new")
    public String submitSurveyForm(@ModelAttribute Survey survey, Authentication authentication) throws Exception {
        // Get the logged-in user
        User creator = userService.findByUsername(authentication.getName());
        survey.setCreator(creator);

        // Remove any questions with input issues
        List<Question> validQuestions = survey.getQuestions().stream()
                .filter(question -> question != null && question.getText() != null && !question.getText().trim().isEmpty())
                .collect(Collectors.toList());

        survey.setQuestions(validQuestions);
        for (Question question : validQuestions) {
            question.setSurvey(survey);
        }
        surveyService.createSurvey(survey);
        return "redirect:/";
    }

    // Participate in a survey
    @GetMapping("/survey/{id}/participate")
    public String participateSurvey(@PathVariable Long id, Model model) {
        Optional<Survey> optionalSurvey = surveyService.getSurveyById(id);
        if (optionalSurvey.isPresent()) {
            Survey survey = optionalSurvey.get();
            model.addAttribute("surveyId", id);
            model.addAttribute("survey", survey);
            return "fillSurvey";
        } else {
            return "redirect:/";
        }
    }

    // View survey results (only for the survey creator)
    @GetMapping("/survey/{id}/results")
    public String viewSurveyResults(@PathVariable("id") Long surveyId, Model model, Authentication authentication) {
        Optional<Survey> optionalSurvey = surveyService.getSurveyById(surveyId);
        if (optionalSurvey.isPresent()) {
            Survey survey = optionalSurvey.get();
            // Check if the authenticated user is the creator of the survey
            if (authentication != null && authentication.getName().equals(survey.getCreator().getUsername())) {
                model.addAttribute("survey", survey);
                // Add other necessary data to the model if needed
                return "result";
            } else {
                return "redirect:/";
            }
        } else {
            return "redirect:/";
        }
    }

    // Close a survey (only for the survey creator)
    @PostMapping("/survey/{id}/close")
    public String closeSurvey(@PathVariable Long id, Authentication authentication) {
        Optional<Survey> optionalSurvey = surveyService.getSurveyById(id);
        if (optionalSurvey.isPresent()) {
            Survey survey = optionalSurvey.get();
            // Check if the authenticated user is the creator of the survey
            if (authentication != null && authentication.getName().equals(survey.getCreator().getUsername())) {
                surveyService.closeSurvey(id);
            }
        }
        return "redirect:/";
    }

    // Survey details page
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
