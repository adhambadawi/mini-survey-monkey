package com.github.adhambadawi.minisurveymonkey.controller;

import com.github.adhambadawi.minisurveymonkey.model.Survey;
import com.github.adhambadawi.minisurveymonkey.model.User;
import com.github.adhambadawi.minisurveymonkey.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/surveys")
public class SurveyController {


    private final SurveyService surveyService;

    @Autowired
    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @PostMapping
    public Survey createSurvey(@RequestBody Survey survey) {
        return surveyService.createSurvey(survey);
    }

    @GetMapping("/{id}")
    public Survey getSurveyById(@PathVariable Long id) {
        Optional<Survey> optionalSurvey = surveyService.getSurveyById(id);
        return optionalSurvey.orElse(null);
    }

    @GetMapping
    public List<Survey> getAllSurveys() {
        return surveyService.getAllSurveys();
    }

    @PutMapping("/{id}")
    public Survey updateSurvey(@PathVariable Long id, @RequestBody Survey surveyDetails) {
        Optional<Survey> optionalSurvey = surveyService.getSurveyById(id);
        if (optionalSurvey.isPresent()) {
            Survey survey = optionalSurvey.get();
            survey.setTitle(surveyDetails.getTitle());
            survey.setClosed(surveyDetails.isClosed());
            survey.setQuestions(surveyDetails.getQuestions());
            survey.setCreator(surveyDetails.getCreator());
            return surveyService.updateSurvey(survey);
        } else {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public void deleteSurvey(@PathVariable Long id) {
        surveyService.deleteSurvey(id);
    }

    @GetMapping("/creator/{creatorId}")
    public List<Survey> getSurveysByCreator(@PathVariable Long creatorId) {
        User creator = new User();
        creator.setId(creatorId);
        return surveyService.getSurveysByCreator(creator);
    }

    @GetMapping("/search")
    public List<Survey> searchSurveysByTitle(@RequestParam String title) {
        return surveyService.searchSurveysByTitle(title);
    }

    @GetMapping("/status")
    public List<Survey> getSurveysByStatus(@RequestParam boolean isClosed) {
        return surveyService.getSurveysByStatus(isClosed);
    }

    @PutMapping("/close/{id}")
    public void closeSurvey(@PathVariable Long id) {
        surveyService.closeSurvey(id);
    }
}
