package com.github.adhambadawi.minisurveymonkey.controller;

import com.github.adhambadawi.minisurveymonkey.model.Question;
import com.github.adhambadawi.minisurveymonkey.model.Response;
import com.github.adhambadawi.minisurveymonkey.service.QuestionService;
import com.github.adhambadawi.minisurveymonkey.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/response")
public class ResponseController {

    private final ResponseService responseService;

    @Autowired
    private ResponseService responseService;
    @Autowired
    private QuestionService questionService;

    @PostMapping("/{questionId}")
    public Response create(@PathVariable Long questionId, @RequestBody Response response) {
        Question question = questionService.get(questionId).get();
        response.setQuestion(question);
        return responseService.create(response);
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
