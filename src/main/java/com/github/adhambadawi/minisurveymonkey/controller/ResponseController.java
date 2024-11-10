package com.github.adhambadawi.minisurveymonkey.controller;

import com.github.adhambadawi.minisurveymonkey.model.Question;
import com.github.adhambadawi.minisurveymonkey.model.Response;
import com.github.adhambadawi.minisurveymonkey.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/response")
public class ResponseController {

    private final ResponseService responseService;

    @Autowired
    public ResponseController(ResponseService responseService) {
        this.responseService = responseService;
    }

    @PostMapping
    public Response create(@RequestBody Response response) {
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
