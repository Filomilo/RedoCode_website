package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.Messages.UtilContainers.Range;
import com.redocode.backend.RequstHandling.Requests.ExerciseCreationRequest;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import com.redocode.backend.database.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration
@Slf4j
// @Disabled("Isotating specific test for debugging")
class ExerciseInfoValidationTest {
  @Autowired ExerciseRepository exerciseRepository;
  @Autowired UsersRepository usersRepository;
  User userCorrect;

  @BeforeAll
  static void setupUserInDb() {}

  @BeforeEach
  void setupCorrectData() {
    userCorrect = usersRepository.findByEmail("correct@example.com");
    if (userCorrect == null) {
      userCorrect =
          User.builder()
              .sessionID("uuid")
              .email("correct@example.com")
              .password("password")
              .nickname("nick")
              .type(User.USER_TYPE.PREMIUM)
              .ProfilePicture(null)
              .build();
      usersRepository.save(userCorrect);
    }

    exerciseCreationRequestCorrect =
        ExerciseCreationRequest.builder()
            .user(userCorrect)
            .outputType(ouptutTypeCorrect)
            .inputType(inputTypeCorrect)
            .ram(ramCorrect)
            .Title(titleCorrect)
            .Description(decritpionCorrect)
            .solutionCodes(solutionCodesCorrect)
            .amountOfAutoTests(amountOfAutoTestsCorrect)
            .breakCharacterInput(breakCharacterInputCorrect)
            .lengthRange(lengthRangeCorrect)
            .lowerCaseInput(lowerCaseInputCorrect)
            .numberInput(numberInputCorrect)
            .spaceInput(spaceInputCorrect)
            .specialCharacterInput(specialCharacterInputCorrect)
            .upperCaseInput(upperCaseInputCorrect)
            .xArrayRange(xArrayRangeCorrect)
            .yArrayRange(yArrayRangeCorrect)
            .testsToRun(Arrays.stream(testsCorrect).toList())
            .timeForExecution(maxExecutionTimeMSCorrect)
            .programResults(new HashMap<>())
            .build();
    exerciseInfoValidation = new ExerciseInfoValidation();
  }

  Variables.VARIABLES_TYPES inputTypeCorrect = Variables.VARIABLES_TYPES.DOUBLE_ARRAY_OF_FLOATS;
  Variables.VARIABLES_TYPES ouptutTypeCorrect = Variables.VARIABLES_TYPES.DOUBLE_ARRAY_OF_FLOATS;
  int ramCorrect = 1024;
  String titleCorrect = "Exercise";
  String decritpionCorrect = "Descritpion";

  int amountOfAutoTestsCorrect = 8;
  boolean breakCharacterInputCorrect = true;
  Range lengthRangeCorrect = new Range(0F, 100F);
  boolean lowerCaseInputCorrect = true;
  boolean numberInputCorrect = true;
  boolean spaceInputCorrect = true;
  boolean specialCharacterInputCorrect = true;
  boolean upperCaseInputCorrect = true;
  Range xArrayRangeCorrect = new Range(1F, 20F);
  Range yArrayRangeCorrect = new Range(1F, 20F);
  Long timeForTaskCorrect = 60L;
  Long maxExecutionTimeMSCorrect = 1000L;

  HashMap<CODE_RUNNER_TYPE, String> solutionCodesCorrect =
      new HashMap<>() {
        {
          put(
              CODE_RUNNER_TYPE.CPP_RUNNER,
              "#include <iostream>\n"
                  + "#include <vector>\n"
                  + "#include <string>\n"
                  + "\n"
                  + "std::vector<std::vector<std::string>>"
                  + " solution(std::vector<std::vector<std::string>> in)\n"
                  + "{\n"
                  + "    return in;\n"
                  + "}");
          put(CODE_RUNNER_TYPE.JS_RUNNER, "function solution(array){return array;}");
        }
      };
  ExerciseTests[] testsCorrect =
      new ExerciseTests[] {
        ExerciseTests.builder()
            .expectedOutput("{\"value\": [[\"1\",\"2\"],[\"3\",\"4\"],[\"5\",\"6\"]]}")
            .input("{\"value\": [[\"1\",\"2\"],[\"3\",\"4\"],[\"5\",\"6\"]]}")
            .excersize(null)
            .build(),
        ExerciseTests.builder()
            .expectedOutput(
                "{\"value\":"
                    + " [[\"1\",\"2\"],[\"3\",\"4\"],[\"5\",\"6\"],[\"1\",\"2\"],[\"3\",\"4\"],[\"5\",\"6\"]]}")
            .input(
                "{\"value\":"
                    + " [[\"1\",\"2\"],[\"3\",\"4\"],[\"5\",\"6\"],[\"1\",\"2\"],[\"3\",\"4\"],[\"5\",\"6\"]]}")
            .excersize(null)
            .build(),
      };

  ExerciseCreationRequest exerciseCreationRequestCorrect;
  ExerciseInfoValidation exerciseInfoValidation;

  @Test
  void handleCorrect() {

    AtomicBoolean res = new AtomicBoolean(false);
    assertDoesNotThrow(
        () -> {
          res.set(exerciseInfoValidation.handle(exerciseCreationRequestCorrect) != null);
        });
    assertTrue(res.get());
  }

  // @Disabled("Isotating specific test for debugging")
  @ParameterizedTest
  @MethodSource(
      "com.redocode.backend.DataProviders.IncorrectExerciseParamatersProviders#incorrectTitleProvider")
  void testIncorrectTitleExerciseCreation(String incorrectTitle) {
    ExerciseCreationRequest exerciseCreationRequest = this.exerciseCreationRequestCorrect;
    exerciseCreationRequest.setTitle(incorrectTitle);
    AtomicBoolean res = new AtomicBoolean(false);
    assertThrows(
        RequestHadndlingException.class,
        () -> {
          res.set(exerciseInfoValidation.handle(exerciseCreationRequestCorrect) != null);
        });
    assertFalse(res.get());
  }

  @ParameterizedTest
  @MethodSource(
      "com.redocode.backend.DataProviders.IncorrectExerciseParamatersProviders#incorrectTitleProvider")
  void testIncorrectDescriptionExerciseCreation(String incorrectdesc) {
    ExerciseCreationRequest exerciseCreationRequest = this.exerciseCreationRequestCorrect;
    exerciseCreationRequest.setDescription(incorrectdesc);
    AtomicBoolean res = new AtomicBoolean(false);
    assertThrows(
        RequestHadndlingException.class,
        () -> {
          res.set(exerciseInfoValidation.handle(exerciseCreationRequestCorrect) != null);
        });
    assertFalse(res.get());
  }

  @ParameterizedTest
  @MethodSource(
      "com.redocode.backend.DataProviders.IncorrectExerciseParamatersProviders#incorrectAmountAutoTest")
  void testIncorrectDAmountOFAutoTestExerciseCreation(int incorrectAmountOfAutoTest) {
    ExerciseCreationRequest exerciseCreationRequest = this.exerciseCreationRequestCorrect;
    exerciseCreationRequest.setAmountOfAutoTests(incorrectAmountOfAutoTest);
    AtomicBoolean res = new AtomicBoolean(false);
    assertThrows(
        RequestHadndlingException.class,
        () -> {
          res.set(exerciseInfoValidation.handle(exerciseCreationRequestCorrect) != null);
        });
    assertFalse(res.get());
  }

  @ParameterizedTest
  @MethodSource(
      "com.redocode.backend.DataProviders.IncorrectExerciseParamatersProviders#incorrectLengthRange")
  void testIncorrectLengthRangeCreation(Range incoorectLegnthRange) {
    ExerciseCreationRequest exerciseCreationRequest = this.exerciseCreationRequestCorrect;
    exerciseCreationRequest.setLengthRange(incoorectLegnthRange);
    AtomicBoolean res = new AtomicBoolean(false);
    assertThrows(
        RequestHadndlingException.class,
        () -> {
          res.set(exerciseInfoValidation.handle(exerciseCreationRequestCorrect) != null);
        });
    assertFalse(res.get());
  }

  @ParameterizedTest
  @MethodSource(
      "com.redocode.backend.DataProviders.IncorrectExerciseParamatersProviders#incorrectArrayRange")
  void testIncorrectArrayXlengthExerciseCreation(Range incorrectArrayRange) {
    ExerciseCreationRequest exerciseCreationRequest = this.exerciseCreationRequestCorrect;
    exerciseCreationRequest.setXArrayRange(incorrectArrayRange);
    AtomicBoolean res = new AtomicBoolean(false);
    assertThrows(
        RequestHadndlingException.class,
        () -> {
          res.set(exerciseInfoValidation.handle(exerciseCreationRequestCorrect) != null);
        });
    assertFalse(res.get());
  }

  @ParameterizedTest
  @MethodSource(
      "com.redocode.backend.DataProviders.IncorrectExerciseParamatersProviders#incorrectArrayRange")
  void testIncorrectArrayYlengthExerciseCreation(Range incorrectArrayRange) {
    ExerciseCreationRequest exerciseCreationRequest = this.exerciseCreationRequestCorrect;
    exerciseCreationRequest.setYArrayRange(incorrectArrayRange);
    AtomicBoolean res = new AtomicBoolean(false);
    assertThrows(
        RequestHadndlingException.class,
        () -> {
          res.set(exerciseInfoValidation.handle(exerciseCreationRequestCorrect) != null);
        });
    assertFalse(res.get());
  }

  @Test
  void testIncorrectBooleanStringExerciseCreation() {
    ExerciseCreationRequest exerciseCreationRequest = this.exerciseCreationRequestCorrect;
    exerciseCreationRequest.setInputType(Variables.VARIABLES_TYPES.SINGLE_STRING);
    exerciseCreationRequest.setUpperCaseInput(false);
    exerciseCreationRequest.setLowerCaseInput(false);
    exerciseCreationRequest.setNumberInput(false);
    exerciseCreationRequest.setSpecialCharacterInput(false);
    exerciseCreationRequest.setBreakCharacterInput(false);
    AtomicBoolean res = new AtomicBoolean(false);
    assertThrows(
        RequestHadndlingException.class,
        () -> {
          res.set(exerciseInfoValidation.handle(exerciseCreationRequestCorrect) != null);
        });
    assertFalse(res.get());
  }

  @ParameterizedTest
  @MethodSource(
      "com.redocode.backend.DataProviders.IncorrectExerciseParamatersProviders#incorrectStringRange")
  void testIncorrectStringRangeExerciseCreation(Range stringRange) {
    ExerciseCreationRequest exerciseCreationRequest = this.exerciseCreationRequestCorrect;
    exerciseCreationRequest.setInputType(Variables.VARIABLES_TYPES.SINGLE_STRING);
    exerciseCreationRequest.setLengthRange(stringRange);
    AtomicBoolean res = new AtomicBoolean(false);
    assertThrows(
        RequestHadndlingException.class,
        () -> {
          res.set(exerciseInfoValidation.handle(exerciseCreationRequestCorrect) != null);
        });
    assertFalse(res.get());
  }

  @ParameterizedTest
  @MethodSource(
      "com.redocode.backend.DataProviders.IncorrectExerciseParamatersProviders#incorrectTime")
  void testIncorrecTimeForCreationExerciseCreation(Long time) {
    ExerciseCreationRequest exerciseCreationRequest = this.exerciseCreationRequestCorrect;
    AtomicBoolean res = new AtomicBoolean(false);
    assertThrows(
        RequestHadndlingException.class,
        () -> {
          res.set(exerciseInfoValidation.handle(exerciseCreationRequestCorrect) != null);
        });
    assertFalse(res.get());
  }

  @ParameterizedTest
  @MethodSource(
      "com.redocode.backend.DataProviders.IncorrectExerciseParamatersProviders#executionTime")
  void testIncorrecExecutionimeForCreationExerciseCreation(Long executionTime) {
    ExerciseCreationRequest exerciseCreationRequest = this.exerciseCreationRequestCorrect;
    exerciseCreationRequest.setTimeForExecution(executionTime);
    AtomicBoolean res = new AtomicBoolean(false);
    assertThrows(
        RequestHadndlingException.class,
        () -> {
          res.set(exerciseInfoValidation.handle(exerciseCreationRequestCorrect) != null);
        });
    assertFalse(res.get());
  }

  @Test
  void testIncorrecSolutionHashSetExerciseCreation() {
    ExerciseCreationRequest exerciseCreationRequest = this.exerciseCreationRequestCorrect;
    exerciseCreationRequest.setSolutionCodes(new HashMap<>());
    AtomicBoolean res = new AtomicBoolean(false);
    assertThrows(
        RequestHadndlingException.class,
        () -> {
          res.set(exerciseInfoValidation.handle(exerciseCreationRequestCorrect) != null);
        });
    assertFalse(res.get());
  }
}
