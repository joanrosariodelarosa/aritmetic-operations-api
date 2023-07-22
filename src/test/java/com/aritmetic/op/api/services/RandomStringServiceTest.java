package com.aritmetic.op.api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RandomStringServiceTest {

    @InjectMocks
    private RandomStringService randomStringService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRandomString() {

        String actualRandomString = randomStringService.getRandomString();

        assertNotNull(actualRandomString);
    }
}