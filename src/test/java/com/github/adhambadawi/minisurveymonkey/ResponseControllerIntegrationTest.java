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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testCreateTextResponse() throws Exception {
        Question question = new Question("Text question", QuestionType.OPEN_ENDED);
        Question savedQuestion = questionRepository.save(question);
        String responseJson = """
        {
            "textResponse": "My text response"
        }
    """;

        mockMvc.perform(post("/api/response/" + savedQuestion.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(responseJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.textResponse").value("My text response"));

        assertEquals(1, responseRepository.count());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testCreateNumberResponse() throws Exception {
        Question question = new Question("Number range question", QuestionType.NUMBER_RANGE);
        question.setMinValue(0);
        question.setMaxValue(10);
        Question savedQuestion = questionRepository.save(question);

        String responseJson = """
        {
            "numberResponse": 5
        }
    """;

        mockMvc.perform(post("/api/response/" + savedQuestion.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(responseJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberResponse").value(5));

        assertEquals(1, responseRepository.count());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testCreateChoiceResponse() throws Exception {
        Question question = new Question("Multiple choice question", QuestionType.MULTIPLE_CHOICE);
        List<String> options = new ArrayList<>();
        options.add("Option A");
        options.add("Option B");
        options.add("Option C");
        question.setOptions(options);
        Question savedQuestion = questionRepository.save(question);

        String responseJson = """
        {
            "choiceResponse": "Option B"
        }
    """;

        mockMvc.perform(post("/api/response/" + savedQuestion.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(responseJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.choiceResponse").value("Option B"));

        assertEquals(1, responseRepository.count());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testGetTextResponse() throws Exception {
        Response response = new Response();
        response.setTextResponse("Example text");
        response = responseRepository.save(response);

        mockMvc.perform(get("/api/response/" + response.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.textResponse").value("Example text"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testGetNumberResponse() throws Exception {
        Response response = new Response();
        response.setNumberResponse(5);
        response = responseRepository.save(response);

        mockMvc.perform(get("/api/response/" + response.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberResponse").value(5));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
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
    @WithMockUser(username = "testuser", roles = {"USER"})
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