package com.github.adhambadawi.minisurveymonkey;

import com.github.adhambadawi.minisurveymonkey.model.Survey;
import com.github.adhambadawi.minisurveymonkey.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserModelTest {

    private User user;
    private Survey survey1;
    private Survey survey2;

    @BeforeEach
    public void setup() {
        // Initialize User and Surveys
        user = new User("testUser", "password123");
        survey1 = new Survey("Customer Feedback Survey");
        survey2 = new Survey("Employee Engagement Survey");

        // Associate Surveys with User
        user.addSurvey(survey1);
        user.addSurvey(survey2);
    }

    @Test
    public void testUserInitialization() {
        // Test user initialization values
        assertEquals("testUser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("ROLE_USER", user.getRole());
        assertEquals(2, user.getSurveys().size());
    }

    @Test
    public void testAddSurveyToUser() {
        // Verify that surveys are added correctly
        assertEquals(2, user.getSurveys().size());
        assertTrue(user.getSurveys().contains(survey1));
        assertTrue(user.getSurveys().contains(survey2));

        // Check that each survey's creator is set to this user
        assertEquals(user, survey1.getCreator());
        assertEquals(user, survey2.getCreator());
    }

    @Test
    public void testRemoveSurveyFromUser() {
        // Remove a survey and verify it is updated
        user.removeSurvey(survey1);

        assertEquals(1, user.getSurveys().size());
        assertFalse(user.getSurveys().contains(survey1));
        assertNull(survey1.getCreator());  // Verify the survey creator is set to null
    }

    @Test
    public void testSetUsername() {
        // Update the username and verify
        user.setUsername("newUsername");
        assertEquals("newUsername", user.getUsername());
    }

    @Test
    public void testSetPassword() {
        // Update the password and verify
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());
    }

    @Test
    public void testSetUserRole() {
        // Change the role and verify
        user.setRole("ROLE_ADMIN");
        assertEquals("ROLE_ADMIN", user.getRole());
    }
}
