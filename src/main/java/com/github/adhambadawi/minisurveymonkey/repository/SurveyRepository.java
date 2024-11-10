package com.github.adhambadawi.minisurveymonkey.repository;

import com.github.adhambadawi.minisurveymonkey.model.Survey;
import com.github.adhambadawi.minisurveymonkey.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findByTitleContainingIgnoreCase(String title);
    List<Survey> findByCreator(User creator);
    List<Survey> findByIsClosed(boolean isClosed);
    List<Survey> findByCreatorAndIsClosed(User creator, boolean isClosed);
}
