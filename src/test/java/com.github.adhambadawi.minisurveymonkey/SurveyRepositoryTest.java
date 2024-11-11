package com.github.adhambadawi.minisurveymonkey;

import com.github.adhambadawi.minisurveymonkey.model.Survey;
import com.github.adhambadawi.minisurveymonkey.model.User;
import com.github.adhambadawi.minisurveymonkey.repository.SurveyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Use embedded DB, like H2
@Rollback(false) // Optional: for observing data in the test database
public class SurveyRepositoryTest {

    @Autowired
    private SurveyRepository surveyRepository;

    private User creator;
    private Survey survey1;
    private Survey survey2;

    @BeforeEach
    public void setup() {
        creator = new User();
        creator.setId(1L); // Assuming you have an ID setter, adjust as needed
        creator.setUsername("testUser");

        survey1 = new Survey();
        survey1.setTitle("Survey 1");
        survey1.setClosed(false);
        survey1.setCreator(creator);

        survey2 = new Survey();
        survey2.setTitle("Another Survey");
        survey2.setClosed(true);
        survey2.setCreator(creator);

        surveyRepository.save(survey1);
        surveyRepository.save(survey2);
    }

    @Test
    public void testFindByTitleContainingIgnoreCase() {
        // Act
        List<Survey> foundSurveys = surveyRepository.findByTitleContainingIgnoreCase("survey");

        // Assert
        assertThat(foundSurveys).isNotEmpty();
        assertThat(foundSurveys).hasSize(2); // Both surveys have "survey" in title, ignoring case
    }

    @Test
    public void testFindByCreator() {
        // Act
        List<Survey> surveysByCreator = surveyRepository.findByCreator(creator);

        // Assert
        assertThat(surveysByCreator).isNotEmpty();
        assertThat(surveysByCreator).containsExactlyInAnyOrder(survey1, survey2);
    }

    @Test
    public void testFindByIsClosed() {
        // Act
        List<Survey> openSurveys = surveyRepository.findByIsClosed(false);
        List<Survey> closedSurveys = surveyRepository.findByIsClosed(true);

        // Assert
        assertThat(openSurveys).contains(survey1);
        assertThat(openSurveys).doesNotContain(survey2);
        assertThat(closedSurveys).contains(survey2);
        assertThat(closedSurveys).doesNotContain(survey1);
    }

    @Test
    public void testFindByCreatorAndIsClosed() {
        // Act
        List<Survey> creatorClosedSurveys = surveyRepository.findByCreatorAndIsClosed(creator, true);
        List<Survey> creatorOpenSurveys = surveyRepository.findByCreatorAndIsClosed(creator, false);

        // Assert
        assertThat(creatorClosedSurveys).contains(survey2);
        assertThat(creatorClosedSurveys).doesNotContain(survey1);
        assertThat(creatorOpenSurveys).contains(survey1);
        assertThat(creatorOpenSurveys).doesNotContain(survey2);
    }
}
