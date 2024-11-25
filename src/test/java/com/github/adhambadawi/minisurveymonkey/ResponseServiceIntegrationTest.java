package com.github.adhambadawi.minisurveymonkey.service;

import com.github.adhambadawi.minisurveymonkey.model.*;
import com.github.adhambadawi.minisurveymonkey.repository.QuestionRepository;
import com.github.adhambadawi.minisurveymonkey.repository.ResponseRepository;
import jakarta.transaction.Transactional;
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
public class ResponseServiceIntegrationTest {

    @Autowired
    private ResponseService responseService;

    @Autowired
    private ResponseRepository responseRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void setUp() {
        responseRepository.deleteAll();
        questionRepository.deleteAll();
    }

    @Test
    void testCreateTextResponse() {
        Response response = new Response();
        response.setTextResponse("My test response");
        Response savedResponse = responseService.create(response);

        assertNotNull(savedResponse.getId());
        assertEquals("My test response", savedResponse.getTextResponse());
    }

    @Test
    void testCreateNumberResponse() {
        Response response = new Response();
        response.setNumberResponse(1);
        Response savedResponse = responseService.create(response);

        assertNotNull(savedResponse.getId());
        assertEquals(1, savedResponse.getNumberResponse());
    }

    @Test
    void testCreateChoiceResponse() {
        Response response = new Response();
        response.setChoiceResponse("My test choice response");
        Response savedResponse = responseService.create(response);

        assertNotNull(savedResponse.getId());
        assertEquals("My test choice response", savedResponse.getChoiceResponse());
    }

    @Test
    void testGetTextResponseById() {
        Response response = new Response();
        response.setTextResponse("My text response");
        Response savedResponse = responseRepository.save(response);

        Optional<Response> retrievedResponse = responseService.get(savedResponse.getId());
        assertTrue(retrievedResponse.isPresent());
        assertEquals("My text response", retrievedResponse.get().getTextResponse());
    }

    @Test
    void testGetNumberResponseById() {
        Response response = new Response();
        response.setNumberResponse(1);
        Response savedResponse = responseRepository.save(response);

        Optional<Response> retrievedResponse = responseService.get(savedResponse.getId());
        assertTrue(retrievedResponse.isPresent());
        assertEquals(1, retrievedResponse.get().getNumberResponse());
    }

    @Test
    void testGetChoiceResponseById() {
        Response response = new Response();
        response.setChoiceResponse("My choice response");
        Response savedResponse = responseRepository.save(response);

        Optional<Response> retrievedResponse = responseService.get(savedResponse.getId());
        assertTrue(retrievedResponse.isPresent());
        assertEquals("My choice response", retrievedResponse.get().getChoiceResponse());
    }

    @Transactional
    @Test
    void testFindResponseByQuestion() {
        Question question = new Question("What is your a name?", QuestionType.OPEN_ENDED);
        Question savedQuestion = questionRepository.save(question);

        Response response1 = new Response();
        response1.setTextResponse("Jaden");
        response1.setQuestion(savedQuestion);

        responseRepository.save(response1);

        List<Response> retrievedResponse = responseService.find(savedQuestion);
        assertEquals(1, retrievedResponse.size());
        assertEquals("Jaden", retrievedResponse.get(0).getTextResponse());
    }
}