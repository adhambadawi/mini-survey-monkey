package com.github.adhambadawi.minisurveymonkey.testing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import com.github.adhambadawi.minisurveymonkey.model.Survey;
import com.github.adhambadawi.minisurveymonkey.service.SurveyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class SurveyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SurveyService surveyService;

    @Test
    public void testCreateSurvey() throws Exception {
        Survey survey = new Survey("New Survey");
        when(surveyService.createSurvey(any(Survey.class))).thenReturn(survey);

        mockMvc.perform(post("/api/surveys")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"New Survey\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Survey"));
    }

    @Test
    public void testGetSurveyById() throws Exception {
        Survey survey = new Survey("Existing Survey");
        survey.setId(1L);
        when(surveyService.getSurveyById(1L)).thenReturn(Optional.of(survey));

        mockMvc.perform(get("/api/surveys/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Existing Survey"));
    }

    @Test
    public void testGetAllSurveys() throws Exception {
        mockMvc.perform(get("/api/surveys"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateSurvey() throws Exception {
        Survey survey = new Survey("Survey to Update");
        survey.setId(1L);
        when(surveyService.getSurveyById(1L)).thenReturn(Optional.of(survey));
        when(surveyService.updateSurvey(any(Survey.class))).thenReturn(survey);

        mockMvc.perform(put("/api/surveys/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated Survey\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Survey"));
    }

    @Test
    public void testDeleteSurvey() throws Exception {
        doNothing().when(surveyService).deleteSurvey(1L);

        mockMvc.perform(delete("/api/surveys/1"))
                .andExpect(status().isOk());

        verify(surveyService, times(1)).deleteSurvey(1L);
    }
}


//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.mockito.Mockito.when;
//import static org.mockito.ArgumentMatchers.any;
//
//import com.github.adhambadawi.minisurveymonkey.model.Survey;
//import com.github.adhambadawi.minisurveymonkey.service.SurveyService;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class SurveyControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Mock
//    private SurveyService surveyService;
//
//    @Test
//    public void testCreateSurvey() throws Exception {
//        // Arrange
//        Survey survey = new Survey();
//        survey.setId(1L);
//        survey.setTitle("New Survey");
//        survey.setClosed(false);
//
//        // Mock the behavior of the service
//        when(surveyService.createSurvey(any(Survey.class))).thenReturn(survey);
//
//        // Act & Assert
//        mockMvc.perform(post("/api/surveys")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(survey)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.title").value("New Survey"))
//                .andExpect(jsonPath("$.closed").value(false));
//    }
//}
//
