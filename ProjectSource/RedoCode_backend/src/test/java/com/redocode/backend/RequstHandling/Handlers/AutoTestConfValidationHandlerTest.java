package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.RequstHandling.Requests.ExerciseCreationRequest;
import com.redocode.backend.database.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration
@Slf4j
// @Disabled("Isotating specific test for debugging")
class AutoTestConfValidationHandlerTest {

  AutoTestConfValidationHandler autoTestConfValidationHandler;
  User user = new User("1");

  @BeforeEach
  void preapreAutoTextConfCalidation() {
    autoTestConfValidationHandler = new AutoTestConfValidationHandler();
  }

  @Test
  void handlecorrecAmountOFTest() {
    ExerciseCreationRequest exerciseCretionRequest =
        ExerciseCreationRequest.builder()
            .ram(1024)
            .amountOfAutoTests(5)
            .Title("123")
            .amountOfAutoTests(22)
            .user(user)
            .Description("!11")
            .timeForTaskMin(60L)
            .timeForExecution(1000L)
            .amountOfAutoTests(6)
            .solutionCodes(new HashMap<>())
                .programResults(new HashMap<>())
            .build();

    assertDoesNotThrow(
        () -> {
          assertNotNull(autoTestConfValidationHandler.handle(exerciseCretionRequest));
        });
  }

  @Test
  void handleIncorrectRam() {
    ExerciseCreationRequest exerciseCretionRequest =
        ExerciseCreationRequest.builder()
            .ram(1024000)
            .amountOfAutoTests(5)
            .Title("123")
            .amountOfAutoTests(22)
            .user(user)
            .Description("!11")
            .timeForTaskMin(60L)
            .timeForExecution(1000L)
            .amountOfAutoTests(6)
            .solutionCodes(new HashMap<>())
                .programResults(new HashMap<>())
            .build();
    assertThrows(
        RequestHadndlingException.class,
        () -> {
          autoTestConfValidationHandler.handle(exerciseCretionRequest);
        });
  }

  @Test
  void handleIncorrecAmountOFTest() {
    ExerciseCreationRequest exerciseCretionRequest =
        ExerciseCreationRequest.builder()
            .ram(1024)
            .amountOfAutoTests(5)
            .Title("123")
            .user(user)
            .Description("!11")
            .timeForTaskMin(60L)
            .timeForExecution(1000L)
            .amountOfAutoTests(-6)
            .solutionCodes(new HashMap<>())
                .programResults(new HashMap<>())
            .build();
    assertThrows(
        RequestHadndlingException.class,
        () -> {
          autoTestConfValidationHandler.handle(exerciseCretionRequest);
        });
  }
}
