package com.github.adhambadawi.minisurveymonkey.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Question text cannot be empty")
    private String text;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    private Integer minValue;
    private Integer maxValue;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "question_options", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "option_text")
    private List<String> options = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "survey_id")
    @JsonBackReference
    private Survey survey;

    @JsonManagedReference
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
