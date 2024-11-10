package com.github.adhambadawi.minisurveymonkey.model;

import jakarta.persistence.*;

@Entity
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String textResponse;
    private Integer numberResponse;
    private String choiceResponse;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public Response() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getTextResponse() {
        return textResponse;
    }

    public Integer getNumberResponse() {
        return numberResponse;
    }

    public String getChoiceResponse() {
        return choiceResponse;
    }

    public Question getQuestion() {
        return question;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTextResponse(String textResponse) {
        this.textResponse = textResponse;
    }

    public void setNumberResponse(Integer numberResponse) {
        this.numberResponse = numberResponse;
    }

    public void setChoiceResponse(String choiceResponse) {
        this.choiceResponse = choiceResponse;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
