package com.github.adhambadawi.minisurveymonkey;

import com.github.adhambadawi.minisurveymonkey.model.Question;
import com.github.adhambadawi.minisurveymonkey.model.QuestionType;
import com.github.adhambadawi.minisurveymonkey.model.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class MultipleChoiceQuestionTest {

    private Question multipleChoiceQuestion;

    @BeforeEach
    public void setup() {
        // Create a multiple choice question with a list of options
        multipleChoiceQuestion = new Question("Choose your favorite fruit", QuestionType.MULTIPLE_CHOICE);
        multipleChoiceQuestion.setOptions(List.of("Apple", "Banana", "Cherry"));
    }

    @Test
    public void testMultipleChoiceQuestionInitialization() {
        assertEquals("Choose your favorite fruit", multipleChoiceQuestion.getText());
        assertEquals(QuestionType.MULTIPLE_CHOICE, multipleChoiceQuestion.getType());
        // Ensure that the options are correctly set
        assertEquals(3, multipleChoiceQuestion.getOptions().size());
        assertTrue(multipleChoiceQuestion.getOptions().contains("Apple"));
        assertTrue(multipleChoiceQuestion.getOptions().contains("Banana"));
        assertTrue(multipleChoiceQuestion.getOptions().contains("Cherry"));
    }

    @Test
    public void testAddChoiceResponseToMultipleChoiceQuestion() {
        Response response = new Response();
        response.setChoiceResponse("Apple");
        multipleChoiceQuestion.addResponse(response);

        // After adding the response, it should appear in the question's responses list
        assertEquals(1, multipleChoiceQuestion.getResponses().size());
        assertTrue(multipleChoiceQuestion.getResponses().contains(response));

        // The response's question reference should be set
        assertEquals(multipleChoiceQuestion, response.getQuestion());
    }

    @Test
    public void testAddMultipleResponses() {
        Response response1 = new Response();
        response1.setChoiceResponse("Banana");
        multipleChoiceQuestion.addResponse(response1);

        Response response2 = new Response();
        response2.setChoiceResponse("Cherry");
        multipleChoiceQuestion.addResponse(response2);

        // Ensure both responses are added correctly
        assertEquals(2, multipleChoiceQuestion.getResponses().size());
        assertTrue(multipleChoiceQuestion.getResponses().contains(response1));
        assertTrue(multipleChoiceQuestion.getResponses().contains(response2));
    }

    @Test
    public void testRemoveResponseFromMultipleChoiceQuestion() {
        Response response = new Response();
        response.setChoiceResponse("Apple");
        multipleChoiceQuestion.addResponse(response);

        // Remove the response and ensure it's no longer associated
        multipleChoiceQuestion.removeResponse(response);

        assertEquals(0, multipleChoiceQuestion.getResponses().size());
        assertFalse(multipleChoiceQuestion.getResponses().contains(response));
        // The response's question reference should now be null
        assertNull(response.getQuestion());
    }

    @Test
    public void testThatInvalidOptionIsStillAddedAtModelLevel() {
        // The model layer doesn't enforce option validity, so adding a response
        // with a non-existent option "Orange" still works at this level.
        Response response = new Response();
        response.setChoiceResponse("Orange");
        multipleChoiceQuestion.addResponse(response);

        // Even though "Orange" isn't in the options, the response is still added because we have no validation in the model
        assertEquals(1, multipleChoiceQuestion.getResponses().size());
        assertTrue(multipleChoiceQuestion.getResponses().contains(response));
    }
}