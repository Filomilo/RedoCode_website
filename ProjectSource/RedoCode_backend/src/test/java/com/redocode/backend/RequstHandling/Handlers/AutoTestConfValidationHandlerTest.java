package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.RequstHandling.Requests.ExerciseCreationRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration
@Slf4j
class AutoTestConfValidationHandlerTest {

    AutoTestConfValidationHandler autoTestConfValidationHandler;


    @BeforeEach
    void preapreAutoTextConfCalidation()
    {
        autoTestConfValidationHandler=new AutoTestConfValidationHandler();
    }
    @Test
    void handlecorrecAmountOFTest() {
        ExerciseCreationRequest exerciseCretionRequest= ExerciseCreationRequest.builder()
                .ram(1024)
                .amountOfAutoTests(5)
                .Title("123")
                .autoTestAmount(22)
                .build();

        assertDoesNotThrow(()->{
            assertTrue(autoTestConfValidationHandler.handle(exerciseCretionRequest));
        });
    }

    @Test
    void handleIncorrectRam() {
        ExerciseCreationRequest exerciseCretionRequest= ExerciseCreationRequest.builder()
                .ram(99999999)
                .build();
        assertThrows(RequestHadndlingException.class,()->{autoTestConfValidationHandler.handle(exerciseCretionRequest);});
    }
    @Test
    void handleIncorrecAmountOFTest() {
        ExerciseCreationRequest exerciseCretionRequest= ExerciseCreationRequest.builder()
                .ram(1024)
                .amountOfAutoTests(222222)
                .build();
        assertThrows(RequestHadndlingException.class,()->{autoTestConfValidationHandler.handle(exerciseCretionRequest);});
    }
}