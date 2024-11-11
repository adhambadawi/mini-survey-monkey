package com.github.adhambadawi.minisurveymonkey;

import com.github.adhambadawi.minisurveymonkey.model.Question;
import com.github.adhambadawi.minisurveymonkey.model.QuestionType;
import com.github.adhambadawi.minisurveymonkey.model.Response;
import com.github.adhambadawi.minisurveymonkey.model.Survey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;



public class QuestionModelTest {

    private Question openEndedQuestion;
    private Question numberRangeQuestion;
    private Question multipleChoiceQuestion;

    @BeforeEach
    public void setup() {
        openEndedQuestion = new Question("What is your favorite color?", QuestionType.OPEN_ENDED);
        numberRangeQuestion = new Question("Rate your satisfaction from 1 to 5", QuestionType.NUMBER_RANGE);
        multipleChoiceQuestion = new Question("Choose your favorite fruit", QuestionType.MULTIPLE_CHOICE);
    }

    @Test
    public void testOpenEndedQuestion() {
        assertEquals("What is your favorite color?", openEndedQuestion.getText());
        assertEquals(QuestionType.OPEN_ENDED, openEndedQuestion.getType());

        // Ensure minValue, maxValue, and options are null or empty for OPEN_ENDED questions
        assertNull(openEndedQuestion.getMinValue());
        assertNull(openEndedQuestion.getMaxValue());
        assertTrue(openEndedQuestion.getOptions().isEmpty());
    }

    @Test
    public void testNumberRangeQuestion() {
        numberRangeQuestion.setMinValue(1);
        numberRangeQuestion.setMaxValue(5);

        assertEquals("Rate your satisfaction from 1 to 5", numberRangeQuestion.getText());
        assertEquals(QuestionType.NUMBER_RANGE, numberRangeQuestion.getType());

        // Ensure minValue and maxValue are set correctly for NUMBER_RANGE questions
        assertEquals(1, numberRangeQuestion.getMinValue());
        assertEquals(5, numberRangeQuestion.getMaxValue());

        // Ensure options list is empty for NUMBER_RANGE questions
        assertTrue(numberRangeQuestion.getOptions().isEmpty());
    }

    @Test
    public void testMultipleChoiceQuestion() {
        multipleChoiceQuestion.setOptions(List.of("Apple", "Banana", "Cherry"));

        assertEquals("Choose your favorite fruit", multipleChoiceQuestion.getText());
        assertEquals(QuestionType.MULTIPLE_CHOICE, multipleChoiceQuestion.getType());

        // Ensure minValue and maxValue are null for MULTIPLE_CHOICE questions
        assertNull(multipleChoiceQuestion.getMinValue());
        assertNull(multipleChoiceQuestion.getMaxValue());

        // Ensure options are set correctly for MULTIPLE_CHOICE questions
        assertEquals(3, multipleChoiceQuestion.getOptions().size());
        assertTrue(multipleChoiceQuestion.getOptions().contains("Apple"));
        assertTrue(multipleChoiceQuestion.getOptions().contains("Banana"));
        assertTrue(multipleChoiceQuestion.getOptions().contains("Cherry"));
    }

    @Test
    public void testAddTextResponseToOpenEndedQuestion() {
        Response response = new Response();
        response.setTextResponse("Blue");
        response.setQuestion(openEndedQuestion);
        openEndedQuestion.addResponse(response);

        assertEquals(1, openEndedQuestion.getResponses().size());
        assertTrue(openEndedQuestion.getResponses().contains(response));
        assertEquals(openEndedQuestion, response.getQuestion());
    }

    @Test
    public void testAddNumberResponseToNumberRangeQuestion() {
        Response response = new Response();
        response.setNumberResponse(4);
        response.setQuestion(numberRangeQuestion);
        numberRangeQuestion.addResponse(response);

        assertEquals(1, numberRangeQuestion.getResponses().size());
        assertTrue(numberRangeQuestion.getResponses().contains(response));
        assertEquals(numberRangeQuestion, response.getQuestion());
    }

    @Test
    public void testAddChoiceResponseToMultipleChoiceQuestion() {
        Response response = new Response();
        response.setChoiceResponse("Apple");
        response.setQuestion(multipleChoiceQuestion);
        multipleChoiceQuestion.addResponse(response);

        assertEquals(1, multipleChoiceQuestion.getResponses().size());
        assertTrue(multipleChoiceQuestion.getResponses().contains(response));
        assertEquals(multipleChoiceQuestion, response.getQuestion());
    }

    @Test
    public void testRemoveResponse() {
        Response response = new Response();
        response.setTextResponse("Blue");
        response.setQuestion(openEndedQuestion);
        openEndedQuestion.addResponse(response);
        openEndedQuestion.removeResponse(response);

        assertEquals(0, openEndedQuestion.getResponses().size());
        assertFalse(openEndedQuestion.getResponses().contains(response));
        assertNull(response.getQuestion());
    }

    @Test
    public void testSurveyAssociation() {
        Survey survey = new Survey("Customer Satisfaction Survey");
        openEndedQuestion.setSurvey(survey);

        assertEquals(survey, openEndedQuestion.getSurvey());
    }

    @Test
    public void testSettersAndGetters() {
        openEndedQuestion.setId(1L);
        assertEquals(1L, openEndedQuestion.getId());

        openEndedQuestion.setText("What is your favorite food?");
        assertEquals("What is your favorite food?", openEndedQuestion.getText());

        openEndedQuestion.setType(QuestionType.MULTIPLE_CHOICE);
        assertEquals(QuestionType.MULTIPLE_CHOICE, openEndedQuestion.getType());
    }
}