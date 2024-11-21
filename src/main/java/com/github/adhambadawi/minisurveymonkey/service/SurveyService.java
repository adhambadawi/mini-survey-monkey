package com.github.adhambadawi.minisurveymonkey.service;

import com.github.adhambadawi.minisurveymonkey.model.Survey;
import com.github.adhambadawi.minisurveymonkey.model.User;
import com.github.adhambadawi.minisurveymonkey.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    public Survey createSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    public Survey updateSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    public void deleteSurvey(Long id) {
        surveyRepository.deleteById(id);
    }

    public Optional<Survey> getSurveyById(Long id) {
        return surveyRepository.findById(id);
    }

    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    public List<Survey> getSurveysByCreator(User creator) {
        return surveyRepository.findByCreator(creator);
    }

    public List<Survey> searchSurveysByTitle(String title) {
        return surveyRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Survey> getSurveysByStatus(boolean isClosed) {
        return surveyRepository.findByIsClosed(isClosed);
    }

    public void closeSurvey(Long id) {
        Optional<Survey> optionalSurvey = surveyRepository.findById(id);
        if (optionalSurvey.isPresent()) {
            Survey survey = optionalSurvey.get();
            survey.setClosed(true);
            surveyRepository.save(survey);
        }
    }

    public void reopenSurvey(Long id) {
        Optional<Survey> optionalSurvey = surveyRepository.findById(id);
        if (optionalSurvey.isPresent()) {
            Survey survey = optionalSurvey.get();
            survey.setClosed(false);
            surveyRepository.save(survey);
        }
    }
}
