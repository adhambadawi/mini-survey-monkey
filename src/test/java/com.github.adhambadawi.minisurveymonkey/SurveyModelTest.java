package com.github.adhambadawi.minisurveymonkey;

import com.github.adhambadawi.minisurveymonkey.model.Survey;
import com.github.adhambadawi.minisurveymonkey.model.User;
import com.github.adhambadawi.minisurveymonkey.model.Question;
import com.github.adhambadawi.minisurveymonkey.model.QuestionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class SurveyModelTest {

    private Survey survey;
    private User user;
    private Question question1;
    private Question question2;

    @BeforeEach
    public void setup() {
        // Initialize User and Survey
        user = new User("testUser", "password123");
        survey = new Survey("Customer Satisfaction Survey");

        // Associate User with Survey
        user.addSurvey(survey);

        // Initialize Questions
        question1 = new Question("How satisfied are you with our service?", QuestionType.NUMBER_RANGE);
        question2 = new Question("What can we improve?", QuestionType.OPEN_ENDED);

        // Add Questions to Survey
        survey.addQuestion(question1);
        survey.addQuestion(question2);
    }

    @Test
    public void testSurveyInitialization() {
        assertEquals("Customer Satisfaction Survey", survey.getTitle());
        assertFalse(survey.isClosed());
        assertEquals(user, survey.getCreator());
        assertEquals(2, survey.getQuestions().size());
    }

    @Test
    public void testSurveyCreatorRelationship() {
        // Ensure the User has the Survey in their list
        assertTrue(user.getSurveys().contains(survey));
        assertEquals(user, survey.getCreator());
    }

    @Test
    public void testAddQuestionToSurvey() {
        // Verify that questions are added correctly
        List<Question> questions = survey.getQuestions();
        assertEquals(2, questions.size());
        assertTrue(questions.contains(question1));
        assertTrue(questions.contains(question2));

        // Check that the survey is set correctly in each question
        assertEquals(survey, question1.getSurvey());
        assertEquals(survey, question2.getSurvey());
    }

    @Test
    public void testRemoveQuestionFromSurvey() {
        // Remove a question and verify the update
        survey.removeQuestion(question1);

        assertEquals(1, survey.getQuestions().size());
        assertFalse(survey.getQuestions().contains(question1));
        assertNull(question1.getSurvey());  // Check that survey is set to null in the question
    }

    @Test
    public void testCloseSurvey() {
        // Set survey to closed and verify
        survey.setClosed(true);
        assertTrue(survey.isClosed());
    }

    @Test
    public void testSetSurveyTitle() {
        // Update title and verify
        survey.setTitle("Employee Feedback Survey");
        assertEquals("Employee Feedback Survey", survey.getTitle());
    }
}
