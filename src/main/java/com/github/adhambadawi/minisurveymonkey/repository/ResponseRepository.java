package com.github.adhambadawi.minisurveymonkey.repository;

import com.github.adhambadawi.minisurveymonkey.model.Response;
import com.github.adhambadawi.minisurveymonkey.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
    List<Response> findByQuestion(Question question);
    List<Response> findByQuestionId(Long questionId);
    List<Response> findByTextResponseContainingIgnoreCase(String text);
    List<Response> findByNumberResponse(Integer number);
    List<Response> findByChoiceResponse(String choice);
}
