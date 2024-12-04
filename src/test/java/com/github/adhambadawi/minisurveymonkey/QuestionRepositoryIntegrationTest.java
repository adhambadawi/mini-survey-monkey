package com.github.adhambadawi.minisurveymonkey;

import com.github.adhambadawi.minisurveymonkey.model.Question;
import com.github.adhambadawi.minisurveymonkey.model.QuestionType;
import com.github.adhambadawi.minisurveymonkey.model.Survey;
import com.github.adhambadawi.minisurveymonkey.model.User;
import com.github.adhambadawi.minisurveymonkey.repository.QuestionRepository;
import com.github.adhambadawi.minisurveymonkey.repository.SurveyRepository;
import com.github.adhambadawi.minisurveymonkey.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class QuestionRepositoryIntegrationTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SurveyRepository surveyRepository;

    @BeforeEach
    void setUp() {
        questionRepository.deleteAll();
    }

    @Test
    void testSaveAndFindQuestion() {
        Question question = new Question();
        question.setText("Integration Test Question");
        questionRepository.save(question);

        Optional<Question> retrievedQuestion = questionRepository.findById(question.getId());

        assertTrue(retrievedQuestion.isPresent());
        assertEquals("Integration Test Question", retrievedQuestion.get().getText());
    }

    @Test
    void testFindByTextContainingIgnoreCase() {
        Question question = new Question();
        question.setText("Integration Test Question");
        questionRepository.save(question);

        List<Question> retrievedQuestions = questionRepository.findByTextContainingIgnoreCase(question.getText());

        assertEquals("Integration Test Question", retrievedQuestions.get(0).getText());
        assertTrue(retrievedQuestions.contains(question));
    }

    @Test
    void testFindByType() {
        Question question1 = new Question("test MCQ Question", QuestionType.MULTIPLE_CHOICE);
        Question question2 = new Question("test Open Ended Question", QuestionType.OPEN_ENDED);
        Question question3 = new Question("test Number Range Question", QuestionType.NUMBER_RANGE);

        questionRepository.save(question1);
        questionRepository.save(question2);
        questionRepository.save(question3);

        //Test MCQ
        Question retrievedMCQ = questionRepository.findByType(QuestionType.MULTIPLE_CHOICE).get(0);
        assertSame(retrievedMCQ.getType(), QuestionType.MULTIPLE_CHOICE);

        //Test MCQ with blank test

        //Test Open Ended
        Question retrievedOpenEnded = questionRepository.findByType(QuestionType.OPEN_ENDED).get(0);
        assertSame(retrievedOpenEnded.getType(), QuestionType.OPEN_ENDED);

        //Test Number Range
        Question retrievedNumberRanged= questionRepository.findByType(QuestionType.NUMBER_RANGE).get(0);
        assertSame(retrievedNumberRanged.getType(), QuestionType.NUMBER_RANGE);
    }

    @Test
     void testFindBySurveyAndType() {
        Question question1 = new Question("test MCQ Question", QuestionType.MULTIPLE_CHOICE);
        Question question2 = new Question("test Open Ended Question", QuestionType.OPEN_ENDED);
        Question question3 = new Question("test Number Range Question", QuestionType.NUMBER_RANGE);
        Survey survey1 = new Survey();
        Survey survey2 = new Survey();

        question1.setSurvey(survey1);
        question2.setSurvey(survey2);
        question3.setSurvey(survey1);

        surveyRepository.save(survey1);
        surveyRepository.save(survey2);
        questionRepository.save(question1);
        questionRepository.save(question2);
        questionRepository.save(question3);

        assertTrue(surveyRepository.findById(survey1.getId()).isPresent());
        List<Question> retrievedSurvey1MCQ = questionRepository.findBySurveyAndType(surveyRepository.findById(survey1.getId()).get(), QuestionType.MULTIPLE_CHOICE);
        assertEquals(1, retrievedSurvey1MCQ.size());
        assertSame(retrievedSurvey1MCQ.get(0).getType(), QuestionType.MULTIPLE_CHOICE);
        assertSame(retrievedSurvey1MCQ.get(0).getSurvey(), survey1);

        assertTrue(surveyRepository.findById(survey2.getId()).isPresent());
        List<Question> retrievedSurvey2OpenEnded = questionRepository.findBySurveyAndType(surveyRepository.findById(survey2.getId()).get(), QuestionType.OPEN_ENDED);
        assertEquals(1, retrievedSurvey2OpenEnded.size());
        assertSame(retrievedSurvey2OpenEnded.get(0).getType(), QuestionType.OPEN_ENDED);
        assertSame(retrievedSurvey2OpenEnded.get(0).getSurvey(), survey2);

        List<Question> retrievedSurvey1NumberRange = questionRepository.findBySurveyAndType(surveyRepository.findById(survey1.getId()).get(), QuestionType.NUMBER_RANGE);
        assertEquals(1, retrievedSurvey1NumberRange.size());
        assertSame(retrievedSurvey1NumberRange.get(0).getType(), QuestionType.NUMBER_RANGE);
        assertSame(retrievedSurvey1NumberRange.get(0).getSurvey(), survey1);

        //test FindBySurvey only
        List<Question> retrievedSurvey1 = questionRepository.findBySurvey(survey1);
        assertEquals(2, retrievedSurvey1.size());
        assertSame(retrievedSurvey1.get(0).getSurvey(), survey1);
    }

    @Test
    void testSavingEmptyQuestion(){
        Question question = new Question("", QuestionType.MULTIPLE_CHOICE);
        Question finalQuestion = question;
        assertThrows(Exception.class, () -> {
            questionRepository.save(finalQuestion);
        });

        question = new Question("non_empty_text", QuestionType.MULTIPLE_CHOICE);
        List<String> options = new ArrayList<>();
        options.add("1");
        options.add("2");
        options.add("3");
        question.setOptions(options);
        questionRepository.save(question);
        assertTrue(questionRepository.findById(question.getId()).isPresent());
        Question q =  questionRepository.findById(question.getId()).get();
        assertEquals(q.getText(), question.getText());
        assertEquals(q.getType(), question.getType());
        assertEquals(3, q.getOptions().size());
    }
    
}
