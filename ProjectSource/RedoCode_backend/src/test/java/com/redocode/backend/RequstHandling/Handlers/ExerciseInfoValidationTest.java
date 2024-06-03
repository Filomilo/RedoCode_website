package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.RequstHandling.Requests.ExerciseCretionRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration
@Slf4j
class ExerciseInfoValidationTest {
    @Test
    void handleCorrect() {
    ExerciseCretionRequest exerciseCretionRequest= ExerciseCretionRequest
            .builder()
            .Title("123457789")
            .Description("123456789")
            .build();

    ExerciseInfoValidation exerciseInfoValidation= new ExerciseInfoValidation();
    AtomicBoolean res = new AtomicBoolean(false);
    assertDoesNotThrow(()->{
        res.set(exerciseInfoValidation.handle(exerciseCretionRequest));
    });
    assertTrue(res.get());
    }
    @Test
    void handleIncorrect() {
        ExerciseCretionRequest exerciseCretionRequest= ExerciseCretionRequest
                .builder()
                .Title("123457789012345678901234567890123456789012345678901234567890")
                .Description("123456789")
                .build();

        ExerciseInfoValidation exerciseInfoValidation= new ExerciseInfoValidation();
        AtomicBoolean res = new AtomicBoolean(false);
       assertThrows(RequestHadndlingException.class,()->exerciseInfoValidation.handle(exerciseCretionRequest));
    }
}