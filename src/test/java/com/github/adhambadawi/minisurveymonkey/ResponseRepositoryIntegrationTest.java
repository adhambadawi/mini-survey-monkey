package com.github.adhambadawi.minisurveymonkey.repository;

import com.github.adhambadawi.minisurveymonkey.model.Question;
import com.github.adhambadawi.minisurveymonkey.model.QuestionType;
import com.github.adhambadawi.minisurveymonkey.model.Response;
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
class ResponseRepositoryIntegrationTest {

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void setUp() {
        responseRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testSaveAndFindByIdOpenEndedResponse() {
        Question question = new Question("What is your name?", QuestionType.OPEN_ENDED);
        Response response = new Response();
        response.setQuestion(question);
        response.setTextResponse("Jaden");

        Response savedResponse = responseRepository.save(response);

        Optional<Response> retrievedResponse = responseRepository.findById(savedResponse.getId());

        assertTrue(retrievedResponse.isPresent());
        assertEquals("Jaden", retrievedResponse.get().getTextResponse());
    }

    @Test
    void testSaveAndFindByIdNumberResponse() {
        Question question = new Question("What is your age?", QuestionType.NUMBER_RANGE);
        Response response = new Response();
        response.setQuestion(question);
        response.setNumberResponse(22);

        Response savedResponse = responseRepository.save(response);

        Optional<Response> retrievedResponse = responseRepository.findById(savedResponse.getId());

        assertTrue(retrievedResponse.isPresent());
        assertEquals(22, retrievedResponse.get().getNumberResponse());
    }

    @Test
    void testSaveAndFindByIdMultipleChoiceResponse() {
        Question question = new Question("What is your favourite colour?", QuestionType.MULTIPLE_CHOICE);
        Response response = new Response();
        response.setQuestion(question);
        response.setChoiceResponse("Purple");

        Response savedResponse = responseRepository.save(response);

        Optional<Response> retrievedResponse = responseRepository.findById(savedResponse.getId());

        assertTrue(retrievedResponse.isPresent());
        assertEquals("Purple", retrievedResponse.get().getChoiceResponse());
    }

    @Test
    void testSaveAndFindByQuestionOpenEndedResponse() {
        Question question = new Question("What is your name?", QuestionType.OPEN_ENDED);
        Response response = new Response();
        response.setQuestion(question);
        response.setTextResponse("Jaden");

        Question savedQuestion = questionRepository.save(question);
        responseRepository.save(response);

        List<Response> retrievedResponses = responseRepository.findByQuestion(savedQuestion);

        assertEquals(1, retrievedResponses.size());
        assertEquals("Jaden", retrievedResponses.get(0).getTextResponse());
    }

    @Test
    void testSaveAndFindByQuestionNumberResponse() {
        Question question = new Question("What is your age?", QuestionType.NUMBER_RANGE);
        Response response = new Response();
        response.setQuestion(question);
        response.setNumberResponse(22);

        Question savedQuestion = questionRepository.save(question);
        responseRepository.save(response);

        List<Response> retrievedResponses = responseRepository.findByQuestion(savedQuestion);

        assertEquals(1, retrievedResponses.size());
        assertEquals(22, retrievedResponses.get(0).getNumberResponse());
    }

    @Test
    void testSaveAndFindByQuestionChoiceResponse() {
        Question question = new Question("What is your favourite colour?", QuestionType.MULTIPLE_CHOICE);
        Response response = new Response();
        response.setQuestion(question);
        response.setChoiceResponse("Purple");

        Question savedQuestion = questionRepository.save(question);
        responseRepository.save(response);

        List<Response> retrievedResponses = responseRepository.findByQuestion(savedQuestion);

        assertEquals(1, retrievedResponses.size());
        assertEquals("Purple", retrievedResponses.get(0).getChoiceResponse());
    }
}