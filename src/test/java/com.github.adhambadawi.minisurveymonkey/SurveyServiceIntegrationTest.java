package com.github.adhambadawi.minisurveymonkey.service;

import com.github.adhambadawi.minisurveymonkey.model.Survey;
import com.github.adhambadawi.minisurveymonkey.model.User;
import com.github.adhambadawi.minisurveymonkey.repository.SurveyRepository;
import com.github.adhambadawi.minisurveymonkey.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class SurveyServiceIntegrationTest {

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private UserRepository userRepository; // Add UserRepository for saving users

    private User creator;

    @BeforeEach
    void setUp() {
        surveyRepository.deleteAll(); // Clear surveys
        userRepository.deleteAll(); // Clear users

        // Save a sample user
        creator = new User("testuser", "password");
        creator = userRepository.save(creator); // Persist the user to the database
    }

    @Test
    void testCreateSurvey() {
        Survey survey = new Survey("Integration Test Survey");
        survey.setCreator(creator);

        Survey savedSurvey = surveyService.createSurvey(survey);

        assertNotNull(savedSurvey.getId()); // Verify the survey was saved
        assertEquals("Integration Test Survey", savedSurvey.getTitle());
    }

    @Test
    void testGetSurveyById() {
        Survey survey = new Survey("Specific Survey");
        survey.setCreator(creator);
        Survey savedSurvey = surveyRepository.save(survey);

        Optional<Survey> retrievedSurvey = surveyService.getSurveyById(savedSurvey.getId());
        assertTrue(retrievedSurvey.isPresent());
        assertEquals("Specific Survey", retrievedSurvey.get().getTitle());
    }

    @Test
    void testGetAllSurveys() {
        Survey survey1 = new Survey("Survey 1");
        survey1.setCreator(creator);

        Survey survey2 = new Survey("Survey 2");
        survey2.setCreator(creator);

        surveyRepository.save(survey1);
        surveyRepository.save(survey2);

        List<Survey> surveys = surveyService.getAllSurveys();
        assertEquals(2, surveys.size());
    }

    @Test
    void testUpdateSurvey() {
        Survey survey = new Survey("Old Title");
        survey.setCreator(creator);
        Survey savedSurvey = surveyRepository.save(survey);

        savedSurvey.setTitle("Updated Title");
        Survey updatedSurvey = surveyService.updateSurvey(savedSurvey);

        assertEquals("Updated Title", updatedSurvey.getTitle());
    }

    @Test
    void testDeleteSurvey() {
        Survey survey = new Survey("To Be Deleted");
        survey.setCreator(creator);
        Survey savedSurvey = surveyRepository.save(survey);

        surveyService.deleteSurvey(savedSurvey.getId());
        Optional<Survey> deletedSurvey = surveyRepository.findById(savedSurvey.getId());

        assertFalse(deletedSurvey.isPresent()); // Verify the survey was deleted
    }

    @Test
    void testSearchSurveysByTitle() {
        Survey survey1 = new Survey("Spring Boot Tutorial");
        Survey survey2 = new Survey("Java Basics");
        survey1.setCreator(creator);
        survey2.setCreator(creator);

        surveyRepository.save(survey1);
        surveyRepository.save(survey2);

        List<Survey> results = surveyService.searchSurveysByTitle("Spring");
        assertEquals(1, results.size());
        assertEquals("Spring Boot Tutorial", results.get(0).getTitle());
    }

    @Test
    void testGetSurveysByStatus() {
        Survey openSurvey = new Survey("Open Survey");
        openSurvey.setClosed(false);
        openSurvey.setCreator(creator);

        Survey closedSurvey = new Survey("Closed Survey");
        closedSurvey.setClosed(true);
        closedSurvey.setCreator(creator);

        surveyRepository.save(openSurvey);
        surveyRepository.save(closedSurvey);

        List<Survey> openSurveys = surveyService.getSurveysByStatus(false);
        List<Survey> closedSurveys = surveyService.getSurveysByStatus(true);

        assertEquals(1, openSurveys.size());
        assertEquals("Open Survey", openSurveys.get(0).getTitle());

        assertEquals(1, closedSurveys.size());
        assertEquals("Closed Survey", closedSurveys.get(0).getTitle());
    }
}