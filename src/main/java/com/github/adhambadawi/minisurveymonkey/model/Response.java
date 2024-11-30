package com.github.adhambadawi.minisurveymonkey.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // For OPEN_ENDED questions
    private String textResponse;

    // For NUMBER_RANGE questions
    private Integer numberResponse;

    // For MULTIPLE_CHOICE questions (Single Choice)
    private String choiceResponse;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "question_id")
    @JsonBackReference
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
