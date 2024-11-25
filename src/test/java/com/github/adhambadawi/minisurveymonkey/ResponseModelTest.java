package com.github.adhambadawi.minisurveymonkey;

import com.github.adhambadawi.minisurveymonkey.model.Response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ResponseModelTest {

    @Test
    public void testOpenEndedResponse() {
        Response response = new Response();
        response.setTextResponse("Example test response");

        assertEquals("Example test response", response.getTextResponse());
        assertNull(response.getChoiceResponse());
        assertNull(response.getNumberResponse());
    }

    @Test
    public void testNumberRangeResponse() {
        Response response = new Response();
        response.setNumberResponse(5);

        assertEquals(5, response.getNumberResponse());
        assertNull(response.getChoiceResponse());
        assertNull(response.getTextResponse());
    }

    @Test
    public void testMultipleChoiceResponse() {
        Response response = new Response();
        response.setChoiceResponse("Option A");

        assertEquals("Option A", response.getChoiceResponse());
        assertNull(response.getTextResponse());
        assertNull(response.getNumberResponse());
    }
}