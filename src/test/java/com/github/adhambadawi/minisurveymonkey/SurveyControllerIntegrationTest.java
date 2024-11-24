package com.github.adhambadawi.minisurveymonkey;

import com.github.adhambadawi.minisurveymonkey.model.Survey;
import com.github.adhambadawi.minisurveymonkey.repository.SurveyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = MiniSurveyMonkeyApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SurveyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SurveyRepository surveyRepository;

    @BeforeEach
    void setUp() {
        surveyRepository.deleteAll(); // Clear the database before each test

        // Seed the database with initial data
        Survey survey1 = new Survey("Survey 1");
        Survey survey2 = new Survey("Survey 2");
        surveyRepository.save(survey1);
        surveyRepository.save(survey2);
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testCreateSurvey() throws Exception {
        String surveyJson = """
        {
            "title": "New Survey"
        }
    """;

        mockMvc.perform(post("/api/surveys")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(surveyJson))
                .andExpect(status().isOk()) // expecting a 200 response (sometime returns 403 when security is enabled)
                .andExpect(jsonPath("$.title").value("New Survey"));

        // Verify that the survey is added to the database
        assertEquals(3, surveyRepository.count());
    }

    @Test
    void testGetSurveyById() throws Exception {
        Survey survey = new Survey("Specific Survey");
        survey = surveyRepository.save(survey); // Save and retrieve the survey ID

        mockMvc.perform(get("/api/surveys/" + survey.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Specific Survey"));
    }

    @Test
    void testGetAllSurveys() throws Exception {
        mockMvc.perform(get("/api/surveys"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2)) // Verify there are 2 surveys
                .andExpect(jsonPath("$[0].title").value("Survey 1"))
                .andExpect(jsonPath("$[1].title").value("Survey 2"));
    }
}