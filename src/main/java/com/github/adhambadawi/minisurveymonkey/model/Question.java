package com.github.adhambadawi.minisurveymonkey.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    private Integer minValue;
    private Integer maxValue;

    @ElementCollection
    private List<String> options = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Response> responses = new ArrayList<>();

    public Question() {}

    public Question(String text, QuestionType type) {
        this.text = text;
        this.type = type;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public QuestionType getType() {
        return type;
    }

    public Integer getMinValue() {
        return minValue;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public List<String> getOptions() {
        return options;
    }

    public Survey getSurvey() {
        return survey;
    }

    public List<Response> getResponses() {
        return responses;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    public void addResponse(Response response) {
        responses.add(response);
        response.setQuestion(this);
    }

    public void removeResponse(Response response) {
        responses.remove(response);
        response.setQuestion(null);
    }
}
