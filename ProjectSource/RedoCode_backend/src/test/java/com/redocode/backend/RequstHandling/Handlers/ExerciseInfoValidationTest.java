package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.RequstHandling.Requests.ExerciseCreationRequest;
import com.redocode.backend.database.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.sql.Time;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration
@Slf4j
class ExerciseInfoValidationTest {
    User user=new User("1");

    @Test
    void handleCorrect() {
    ExerciseCreationRequest exerciseCretionRequest= ExerciseCreationRequest
            .builder()
            .Title("123457789")
            .Description("123456789")
            .user(user)
            .timeForTask(new Time(1000))
            .timeForExecution(1000L)
            .solutionCodes(new HashMap<>())
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
        ExerciseCreationRequest exerciseCretionRequest= ExerciseCreationRequest
                .builder()
                .Title("123457789012345678901234567890123456789012345678901234567890")
                .Description("123456789")
                .user(user)
                .timeForTask(new Time(1000))
                .timeForExecution(1000L)
                .solutionCodes(new HashMap<>())
                .build();

        ExerciseInfoValidation exerciseInfoValidation= new ExerciseInfoValidation();
        AtomicBoolean res = new AtomicBoolean(false);
       assertThrows(RequestHadndlingException.class,()->exerciseInfoValidation.handle(exerciseCretionRequest));
    }
}