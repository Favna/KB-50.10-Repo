package com.example.method.worksurge;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    private String regex = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";


    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void emailValid1() throws Exception {
        String email = "john@john.nl";
        assertTrue(email.matches(regex));
    }

    @Test
    public void emailValid2() throws Exception {
        String email = "JOHN@john.nl";
        assertFalse(email.matches(regex));
    }

    @Test
    public void emailValid3() throws Exception {
        String email = "john@john.nl.com";
        assertTrue(email.matches(regex));
    }

    @Test
    public void emailValid4() throws Exception {
        String email ="John@.nl";
        assertFalse(email.matches(regex));
    }

    @Test
    public void emailValid5() throws Exception {
        String email = "john.nl";
        assertFalse(email.matches(regex));
    }
}