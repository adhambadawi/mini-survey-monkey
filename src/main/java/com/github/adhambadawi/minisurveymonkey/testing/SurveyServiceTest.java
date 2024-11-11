package com.github.adhambadawi.minisurveymonkey.testing;
import com.github.adhambadawi.minisurveymonkey.model.Survey;
import com.github.adhambadawi.minisurveymonkey.model.User;
import com.github.adhambadawi.minisurveymonkey.repository.SurveyRepository;
import com.github.adhambadawi.minisurveymonkey.service.SurveyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SurveyServiceTest {

    @Mock
    private SurveyRepository surveyRepository;

    @InjectMocks
    private SurveyService surveyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSurvey() {
        Survey survey = new Survey("Sample Survey");
        when(surveyRepository.save(survey)).thenReturn(survey);

        Survey createdSurvey = surveyService.createSurvey(survey);

        assertNotNull(createdSurvey);
        assertEquals("Sample Survey", createdSurvey.getTitle());
        verify(surveyRepository, times(1)).save(survey);
    }

    @Test
    void testGetSurveyById() {
        Survey survey = new Survey("Sample Survey");
        when(surveyRepository.findById(1L)).thenReturn(Optional.of(survey));

        Optional<Survey> foundSurvey = surveyService.getSurveyById(1L);

        assertTrue(foundSurvey.isPresent());
        assertEquals("Sample Survey", foundSurvey.get().getTitle());
        verify(surveyRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllSurveys() {
        Survey survey1 = new Survey("Survey 1");
        Survey survey2 = new Survey("Survey 2");
        when(surveyRepository.findAll()).thenReturn(Arrays.asList(survey1, survey2));

        List<Survey> surveys = surveyService.getAllSurveys();

        assertEquals(2, surveys.size());
        verify(surveyRepository, times(1)).findAll();
    }

    @Test
    void testDeleteSurvey() {
        surveyService.deleteSurvey(1L);
        verify(surveyRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetSurveysByCreator() {
        User creator = new User();
        creator.setId(1L);
        Survey survey = new Survey("Survey by creator");
        when(surveyRepository.findByCreator(creator)).thenReturn(List.of(survey));

        List<Survey> surveys = surveyService.getSurveysByCreator(creator);

        assertEquals(1, surveys.size());
        assertEquals("Survey by creator", surveys.get(0).getTitle());
        verify(surveyRepository, times(1)).findByCreator(creator);
    }

    @Test
    void testCloseSurvey() {
        Survey survey = new Survey("Survey to close");
        when(surveyRepository.findById(1L)).thenReturn(Optional.of(survey));
        surveyService.closeSurvey(1L);

        assertTrue(survey.isClosed());
        verify(surveyRepository, times(1)).save(survey);
    }

    @Test
    void testSearchSurveysByTitle() {
        Survey survey = new Survey("Sample Survey");
        when(surveyRepository.findByTitleContainingIgnoreCase("Sample")).thenReturn(List.of(survey));

        List<Survey> surveys = surveyService.searchSurveysByTitle("Sample");

        assertEquals(1, surveys.size());
        assertEquals("Sample Survey", surveys.get(0).getTitle());
        verify(surveyRepository, times(1)).findByTitleContainingIgnoreCase("Sample");
    }

    @Test
    void testGetSurveysByStatus() {
        Survey survey = new Survey("Open Survey");
        survey.setClosed(false);
        when(surveyRepository.findByIsClosed(false)).thenReturn(List.of(survey));

        List<Survey> openSurveys = surveyService.getSurveysByStatus(false);

        assertEquals(1, openSurveys.size());
        assertFalse(openSurveys.get(0).isClosed());
        verify(surveyRepository, times(1)).findByIsClosed(false);
    }
}
//package com.github.adhambadawi.minisurveymonkey.testing;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//import com.github.adhambadawi.minisurveymonkey.model.Survey;
//import com.github.adhambadawi.minisurveymonkey.model.User;
//import com.github.adhambadawi.minisurveymonkey.repository.SurveyRepository;
//import com.github.adhambadawi.minisurveymonkey.service.SurveyService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//public class SurveyServiceTest {
//
//    @Mock
//    private SurveyRepository surveyRepository;
//
//    @InjectMocks
//    private SurveyService surveyService;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testCreateSurvey() {
//        Survey survey = new Survey("Test Survey");
//        when(surveyRepository.save(survey)).thenReturn(survey);
//
//        Survey createdSurvey = surveyService.createSurvey(survey);
//        assertNotNull(createdSurvey);
//        assertEquals("Test Survey", createdSurvey.getTitle());
//    }
//
//    @Test
//    public void testUpdateSurvey() {
//        Survey survey = new Survey("Original Title");
//        survey.setId(1L);
//        when(surveyRepository.save(survey)).thenReturn(survey);
//
//        survey.setTitle("Updated Title");
//        Survey updatedSurvey = surveyService.updateSurvey(survey);
//        assertEquals("Updated Title", updatedSurvey.getTitle());
//    }
//
//    @Test
//    public void testDeleteSurvey() {
//        Long surveyId = 1L;
//        doNothing().when(surveyRepository).deleteById(surveyId);
//
//        surveyService.deleteSurvey(surveyId);
//        verify(surveyRepository, times(1)).deleteById(surveyId);
//    }
//
//    @Test
//    public void testGetSurveyById() {
//        Survey survey = new Survey("Sample Survey");
//        survey.setId(1L);
//        when(surveyRepository.findById(1L)).thenReturn(Optional.of(survey));
//
//        Optional<Survey> foundSurvey = surveyService.getSurveyById(1L);
//        assertTrue(foundSurvey.isPresent());
//        assertEquals("Sample Survey", foundSurvey.get().getTitle());
//    }
//
//    @Test
//    public void testGetAllSurveys() {
//        List<Survey> surveys = new ArrayList<>();
//        surveys.add(new Survey("Survey 1"));
//        surveys.add(new Survey("Survey 2"));
//        when(surveyRepository.findAll()).thenReturn(surveys);
//
//        List<Survey> allSurveys = surveyService.getAllSurveys();
//        assertEquals(2, allSurveys.size());
//        assertEquals("Survey 1", allSurveys.get(0).getTitle());
//    }
//
//    @Test
//    public void testGetSurveysByCreator() {
//        User creator = new User();
//        creator.setId(1L);
//
//        List<Survey> surveys = new ArrayList<>();
//        surveys.add(new Survey("Survey 1"));
//        when(surveyRepository.findByCreator(creator)).thenReturn(surveys);
//
//        List<Survey> surveysByCreator = surveyService.getSurveysByCreator(creator);
//        assertEquals(1, surveysByCreator.size());
//        assertEquals("Survey 1", surveysByCreator.get(0).getTitle());
//    }
//
//    @Test
//    public void testSearchSurveysByTitle() {
//        List<Survey> surveys = new ArrayList<>();
//        surveys.add(new Survey("Title Test Survey"));
//        when(surveyRepository.findByTitleContainingIgnoreCase("test")).thenReturn(surveys);
//
//        List<Survey> foundSurveys = surveyService.searchSurveysByTitle("test");
//        assertEquals(1, foundSurveys.size());
//        assertEquals("Title Test Survey", foundSurveys.get(0).getTitle());
//    }
//
//    @Test
//    public void testGetSurveysByStatus() {
//        List<Survey> openSurveys = new ArrayList<>();
//        Survey openSurvey = new Survey("Open Survey");
//        openSurvey.setClosed(false);
//        openSurveys.add(openSurvey);
//
//        when(surveyRepository.findByIsClosed(false)).thenReturn(openSurveys);
//
//        List<Survey> foundOpenSurveys = surveyService.getSurveysByStatus(false);
//        assertEquals(1, foundOpenSurveys.size());
//        assertFalse(foundOpenSurveys.get(0).isClosed());
//        assertEquals("Open Survey", foundOpenSurveys.get(0).getTitle());
//    }
//
//    @Test
//    public void testCloseSurvey() {
//        Survey survey = new Survey("Survey to Close");
//        survey.setId(1L);
//        survey.setClosed(false);
//
//        when(surveyRepository.findById(1L)).thenReturn(Optional.of(survey));
//        when(surveyRepository.save(survey)).thenReturn(survey);
//
//        surveyService.closeSurvey(1L);
//
//        assertTrue(survey.isClosed());
//        verify(surveyRepository, times(1)).save(survey);
//    }
//}
