package com.github.adhambadawi.minisurveymonkey;

import com.github.adhambadawi.minisurveymonkey.model.Question;
import com.github.adhambadawi.minisurveymonkey.model.QuestionType;
import com.github.adhambadawi.minisurveymonkey.model.Response;
import com.github.adhambadawi.minisurveymonkey.repository.QuestionRepository;
import com.github.adhambadawi.minisurveymonkey.repository.ResponseRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = MiniSurveyMonkeyApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ResponseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ResponseRepository responseRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void setUp() {
        responseRepository.deleteAll();
        questionRepository.deleteAll();
    }
/**
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testCreateTextResponse() throws Exception {
        String responseJson = """
        {
            "textResponse": "My text response"
        }
    """;

        mockMvc.perform(post("/api/response/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(responseJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.textResponse").value("My text response"));

        assertEquals(1, responseRepository.count());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testCreateNumberResponse() throws Exception {
        String responseJson = """
        {
            "numberResponse": 5
        }
    """;

        mockMvc.perform(post("/api/response/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(responseJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberResponse").value(5));

        assertEquals(1, responseRepository.count());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testCreateChoiceResponse() throws Exception {
        String responseJson = """
        {
            "choiceResponse": "My choice response"
        }
    """;

        mockMvc.perform(post("/api/response/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(responseJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.choiceResponse").value("My choice response"));

        assertEquals(1, responseRepository.count());
    }
*/
    @Test
    void testGetTextResponse() throws Exception {
        Response response = new Response();
        response.setTextResponse("Example text");
        response = responseRepository.save(response);

        mockMvc.perform(get("/api/response/" + response.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.textResponse").value("Example text"));
    }

    @Test
    void testGetNumberResponse() throws Exception {
        Response response = new Response();
        response.setNumberResponse(5);
        response = responseRepository.save(response);

        mockMvc.perform(get("/api/response/" + response.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberResponse").value(5));
    }

    @Test
    void testGetChoiceResponse() throws Exception {
        Response response = new Response();
        response.setChoiceResponse("Example choice");
        response = responseRepository.save(response);

        mockMvc.perform(get("/api/response/" + response.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.choiceResponse").value("Example choice"));
    }

    @Transactional
    @Test
    void testFindResponse() throws Exception {
        Question question = new Question("What is your a name?", QuestionType.OPEN_ENDED);
        Question savedQuestion = questionRepository.save(question);

        Response response1 = new Response();
        response1.setTextResponse("Jaden");
        response1.setQuestion(savedQuestion);

        responseRepository.save(response1);

        Response response2 = new Response();
        response2.setTextResponse("Bob");
        response2.setQuestion(savedQuestion);

        responseRepository.save(response2);

        mockMvc.perform(get("/api/response?question=" + question.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].textResponse").value("Jaden"))
                .andExpect(jsonPath("$[1].textResponse").value("Bob"));
    }
}