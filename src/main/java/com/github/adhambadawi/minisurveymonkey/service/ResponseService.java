package com.github.adhambadawi.minisurveymonkey.service;

import com.github.adhambadawi.minisurveymonkey.model.Question;
import com.github.adhambadawi.minisurveymonkey.model.Response;
import com.github.adhambadawi.minisurveymonkey.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResponseService {

    @Autowired
    private ResponseRepository responseRepository;

    public Response create(Response response) {
        return responseRepository.save(response);
    }

    public Optional<Response> get(Long id) {
        return responseRepository.findById(id);
    }

    public List<Response> find(Question question) {
        return responseRepository.findByQuestion(question);
    }
}
