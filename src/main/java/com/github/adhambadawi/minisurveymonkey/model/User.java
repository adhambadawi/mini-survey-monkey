package com.github.adhambadawi.minisurveymonkey.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "app_user") // "user" is a reserved keyword in some databases
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role = "ROLE_USER";

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Survey> surveys = new ArrayList<>();

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Survey> getSurveys() {
        return surveys;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSurveys(List<Survey> surveys) {
        this.surveys = surveys;
    }

    public void addSurvey(Survey survey) {
        surveys.add(survey);
        survey.setCreator(this);
    }

    public void removeSurvey(Survey survey) {
        surveys.remove(survey);
        survey.setCreator(null);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
