package com.github.adhambadawi.minisurveymonkey.repository;

import com.github.adhambadawi.minisurveymonkey.model.Survey;
import com.github.adhambadawi.minisurveymonkey.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class SurveyRepositoryIntegrationTest {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private UserRepository userRepository;

    private User creator;

    @BeforeEach
    void setUp() {
        surveyRepository.deleteAll();
        userRepository.deleteAll();

        // Create and save a User (creator)
        creator = new User("testuser", "password");
        creator = userRepository.save(creator);
    }

    @Test
    void testSaveAndFindSurvey() {
        Survey survey = new Survey();
        survey.setTitle("Integration Test Survey");
        survey.setCreator(creator);

        Survey savedSurvey = surveyRepository.save(survey);

        // Fetch the survey from the database
        Optional<Survey> retrievedSurvey = surveyRepository.findById(savedSurvey.getId());

        // Verify the survey was saved and retrieved correctly
        assertTrue(retrievedSurvey.isPresent());
        assertEquals("Integration Test Survey", retrievedSurvey.get().getTitle());
    }

    @Test
    void testFindByTitleContainingIgnoreCase() {
        Survey survey1 = new Survey("Spring Boot Tutorial");
        Survey survey2 = new Survey("Java Basics");
        survey1.setCreator(creator);
        survey2.setCreator(creator);
        surveyRepository.save(survey1);
        surveyRepository.save(survey2);

        // Fetch the survey from the database
        List<Survey> results = surveyRepository.findByTitleContainingIgnoreCase("spring");

        // Verify the survey was retrieved correctly
        assertEquals(1, results.size());
        assertEquals("Spring Boot Tutorial", results.get(0).getTitle());
    }

    @Test
    void testFindByCreator() {
        Survey survey1 = new Survey("Survey 1");
        survey1.setCreator(creator);
        Survey survey2 = new Survey("Survey 2");
        survey2.setCreator(creator);
        surveyRepository.save(survey1);
        surveyRepository.save(survey2);

        // Fetch the survey from the database
        List<Survey> results = surveyRepository.findByCreator(creator);

        // Verify the survey was retrieved correctly
        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(s -> s.getTitle().equals("Survey 1")));
        assertTrue(results.stream().anyMatch(s -> s.getTitle().equals("Survey 2")));
    }

    @Test
    void testFindByIsClosed() {
        Survey openSurvey = new Survey("Open Survey");
        openSurvey.setClosed(false);
        openSurvey.setCreator(creator);

        Survey closedSurvey = new Survey("Closed Survey");
        closedSurvey.setClosed(true);
        closedSurvey.setCreator(creator);
        surveyRepository.save(openSurvey);
        surveyRepository.save(closedSurvey);

        List<Survey> openSurveys = surveyRepository.findByIsClosed(false);
        List<Survey> closedSurveys = surveyRepository.findByIsClosed(true);

        assertEquals(1, openSurveys.size());
        assertEquals("Open Survey", openSurveys.get(0).getTitle());

        assertEquals(1, closedSurveys.size());
        assertEquals("Closed Survey", closedSurveys.get(0).getTitle());
    }

    @Test
    void testFindByCreatorAndIsClosed() {
        Survey openSurvey = new Survey("Open Survey");
        openSurvey.setClosed(false);
        openSurvey.setCreator(creator);

        Survey closedSurvey = new Survey("Closed Survey");
        closedSurvey.setClosed(true);
        closedSurvey.setCreator(creator);

        surveyRepository.save(openSurvey);
        surveyRepository.save(closedSurvey);

        List<Survey> results = surveyRepository.findByCreatorAndIsClosed(creator, true);

        assertEquals(1, results.size());
        assertEquals("Closed Survey", results.get(0).getTitle());
    }
}