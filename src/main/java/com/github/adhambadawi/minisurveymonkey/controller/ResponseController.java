package com.github.adhambadawi.minisurveymonkey.controller;

import com.github.adhambadawi.minisurveymonkey.model.Question;
import com.github.adhambadawi.minisurveymonkey.model.Response;
import com.github.adhambadawi.minisurveymonkey.service.QuestionService;
import com.github.adhambadawi.minisurveymonkey.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/response")
public class ResponseController {

    @Autowired
    private ResponseService responseService;
    @Autowired
    private QuestionService questionService;

    @PostMapping("/{questionId}")
    public ResponseEntity<?> create(@PathVariable Long questionId, @RequestBody Map<String, Object> payload) {
        Optional<Question> optionalQuestion = questionService.get(questionId);
        if (!optionalQuestion.isPresent()) {
            return ResponseEntity.badRequest().body("Invalid question ID");
        }

        Question question = optionalQuestion.get();
        Response response = new Response();
        response.setQuestion(question);

        String type = question.getType().toString();
        if ("OPEN_ENDED".equals(type)) {
            String textResponse = (String) payload.get("textResponse");
            if (textResponse == null || textResponse.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Text response is required");
            }
            response.setTextResponse(textResponse);
        } else if ("NUMBER_RANGE".equals(type)) {
            Integer numberResponse = (Integer) payload.get("numberResponse");
            if (numberResponse == null) {
                return ResponseEntity.badRequest().body("Number response is required");
            }
            if (numberResponse < question.getMinValue() || numberResponse > question.getMaxValue()) {
                return ResponseEntity.badRequest().body("Number response is out of allowed range");
            }
            response.setNumberResponse(numberResponse);
        } else if ("MULTIPLE_CHOICE".equals(type)) {
            String selectedOption = (String) payload.get("choiceResponse");
            if (selectedOption == null || selectedOption.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("An option must be selected");
            }
            // Validate the selected option
            if (!question.getOptions().contains(selectedOption)) {
                return ResponseEntity.badRequest().body("Invalid option selected");
            }
            response.setChoiceResponse(selectedOption);
        } else {
            return ResponseEntity.badRequest().body("Unsupported question type");
        }

        Response savedResponse = responseService.create(response);
        return ResponseEntity.ok(savedResponse);
    }

    @GetMapping("/{id}")
    public Response get(@PathVariable Long id) {
        return responseService.get(id).orElse(null);
    }

    @GetMapping
    public List<Response> find(@RequestParam("question") Question question) {
        return responseService.find(question);
    }
}
