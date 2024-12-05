package com.github.adhambadawi.minisurveymonkey;

import com.github.adhambadawi.minisurveymonkey.model.Question;
import com.github.adhambadawi.minisurveymonkey.model.QuestionType;
import com.github.adhambadawi.minisurveymonkey.model.Survey;
import com.github.adhambadawi.minisurveymonkey.model.User;
import com.github.adhambadawi.minisurveymonkey.repository.QuestionRepository;
import com.github.adhambadawi.minisurveymonkey.service.QuestionService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")

public class QuestionServiceIntegrationTest {

    @Autowired
    private QuestionService questionService;

    @Test
    void testGetQuestionById() {
        Question question1 = new Question("Service Integration Test", QuestionType.MULTIPLE_CHOICE);
        questionService.createQuestion(question1);

        questionService.get(question1.getId());
        assertTrue(questionService.get(question1.getId()).isPresent());
        Question q =  questionService.get(question1.getId()).get();
        assertEquals(q.getText(), question1.getText());
        assertEquals(q.getType(), question1.getType());
    }

    @Test
    void testSaveMCQ() {
        Question question2 = new Question("", QuestionType.MULTIPLE_CHOICE);
        assertThrows(Exception.class, () -> {
            questionService.createQuestion(question2);
        });
        question2.setText("non_empty_text");
        List<String> options = new ArrayList<>();
        options.add("1");
        options.add("2");
        options.add("3");
        question2.setOptions(options);
        questionService.createQuestion(question2);
        assertTrue(questionService.get(question2.getId()).isPresent());
        Question q =  questionService.get(question2.getId()).get();
        assertEquals(q.getText(), question2.getText());
        assertEquals(q.getType(), question2.getType());
        assertEquals(3, q.getOptions().size());
    }
}
