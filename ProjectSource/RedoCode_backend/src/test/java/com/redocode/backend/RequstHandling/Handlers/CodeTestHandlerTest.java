package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.RequstHandling.Requests.CodeTestRequest;
import com.redocode.backend.database.ExerciseTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CodeTestHandlerTest {

    ExerciseTests[] tests;
    @BeforeEach
    void prepareTests()
    {

    }

    @Test
    void handle() {


        CodeTestRequest codeTestRequest=CodeTestRequest.builder()
                .testsToRun()
                .build()


    }
}