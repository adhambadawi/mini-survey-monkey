package com.github.adhambadawi.minisurveymonkey.repository;

import com.github.adhambadawi.minisurveymonkey.model.Question;
import com.github.adhambadawi.minisurveymonkey.model.QuestionType;
import com.github.adhambadawi.minisurveymonkey.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findBySurvey(Survey survey);
    List<Question> findByType(QuestionType type);
    List<Question> findBySurveyAndType(Survey survey, QuestionType type);
    List<Question> findByTextContainingIgnoreCase(String text);
}
