package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.RequstHandling.Requests.CodeRunnerRequest;
import com.redocode.backend.RequstHandling.Requests.CodeTestRequest;
import com.redocode.backend.RequstHandling.Requests.Interfaces.ICodeResultsRequest;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunner;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import com.redocode.backend.VmAcces.CodeRunnersController;
import com.redocode.backend.database.ExerciseTests;
import com.redocode.backend.database.User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
// @Disabled("not working under guthub pipilene")
@Disabled("Not workign along isde other test")
// @DisabledOnOs(OS.LINUX)
class CodeTestHandlerTest {

  @Autowired CodeRunnersController codeRunnersController;
  ExerciseTests[] tests;

  @BeforeEach
  void prepareTests() {
    tests =
        new ExerciseTests[] {
          ExerciseTests.builder()
              .id(1l)
              .expectedOutput("[1,2]")
              .input("[1,2]")
              .excersize(null)
              .build(),
          ExerciseTests.builder()
              .id(2l)
              .expectedOutput("[1,2,3]")
              .input("[1,2,3]")
              .excersize(null)
              .build(),
          ExerciseTests.builder()
              .id(3l)
              .expectedOutput("[1,2,3,4]")
              .input("[1,2,3,4]")
              .excersize(null)
              .build(),
          ExerciseTests.builder()
              .id(4l)
              .expectedOutput("[1,2,3,4,5]")
              .input("[1,2,3,4,5]")
              .excersize(null)
              .build(),
        };
  }

  @Test
  void handleCpp() throws RequestHadndlingException {
    CodeRunnerAccesValidationHandler codeRunnerAccesValidationHandler =
        new CodeRunnerAccesValidationHandler();
    CodeTestHandler codeTestHandler = new CodeTestHandler();

    User user = new User("1234" + UUID.randomUUID());

    HashMap<CODE_RUNNER_TYPE, String> solutions = new HashMap<>();
    solutions.put(
        CODE_RUNNER_TYPE.CPP_RUNNER,
        "#include <iostream>\n"
            + "#include <vector>\n"
            + "\n"
            + "std::vector<int> solution(std::vector<int> in)\n"
            + "{\n"
            + "    return in;\n"
            + "}");

    CodeTestRequest codeTestRequest =
        CodeTestRequest.builder()
            .testsToRun(Arrays.stream(this.tests).toList())
            .codeRunnerType(CODE_RUNNER_TYPE.CPP_RUNNER)
            .user(user)
            .inputType(Variables.VARIABLES_TYPES.ARRAY_OF_INTEGERS)
            .outputType(Variables.VARIABLES_TYPES.ARRAY_OF_INTEGERS)
            .timeForExecution(500l)
            .timeForTaskMin(10L)
            .solutionCodes(solutions)
            .build();

    assertNotNull(
        codeRunnerAccesValidationHandler.handle(
            CodeRunnerRequest.builder()
                .codeRunnerType(CODE_RUNNER_TYPE.CPP_RUNNER)
                .user(user)
                .build()));
    CodeRunner codeRunner = codeRunnersController.getUserCodeRunner(user);
    assertNotNull(codeRunner);

    assertNotNull(codeTestHandler.handle(codeTestRequest));
  }

  @Test
  @SneakyThrows
  void handleCompliationError() {
    CodeRunnerAccesValidationHandler codeRunnerAccesValidationHandler =
        new CodeRunnerAccesValidationHandler();
    CodeTestHandler codeTestHandler = new CodeTestHandler();
    codeTestHandler.set_continueOnError(true);
    User user = new User("1234" + UUID.randomUUID());

    HashMap<CODE_RUNNER_TYPE, String> solutions = new HashMap<>();
    solutions.put(
        CODE_RUNNER_TYPE.CPP_RUNNER,
        "#include <iostream>\n"
            + "#include <vector>\n"
            + "\n"
            + "std::vector<int> solution(std::vector<int> in)\n"
            + "{\n"
            + "    return i;\n"
            + "}");

    CodeTestRequest codeTestRequest =
        CodeTestRequest.builder()
            .testsToRun(Arrays.stream(this.tests).toList())
            .codeRunnerType(CODE_RUNNER_TYPE.CPP_RUNNER)
            .user(user)
            .inputType(Variables.VARIABLES_TYPES.ARRAY_OF_INTEGERS)
            .outputType(Variables.VARIABLES_TYPES.ARRAY_OF_INTEGERS)
            .timeForExecution(500l)
            .timeForTaskMin(10L)
            .solutionCodes(solutions)
            .build();

    codeRunnerAccesValidationHandler.handle(
        CodeRunnerRequest.builder().codeRunnerType(CODE_RUNNER_TYPE.CPP_RUNNER).user(user).build());
    CodeRunner codeRunner = codeRunnersController.getUserCodeRunner(user);
    assertNotNull(codeRunner);
    ICodeResultsRequest porgramReusltsSendRequest =
        (ICodeResultsRequest) codeTestHandler.handle(codeTestRequest);
    assertEquals(1, porgramReusltsSendRequest.getProgramResults().size());
    assertTrue(
        porgramReusltsSendRequest
            .getProgramResults()
                .get(codeTestRequest.getCodeRunnerType())
            .get(0)
            .getConsoleOutput()
            .getErrorOutput()
            .contains("error: 'i' was not declared in this scope; did you mean 'in'?"));
    log.info("porgramReusltsSendRequest: " + porgramReusltsSendRequest);
  }

  @AfterEach
  void removePoteniatlDockerContaierd() {
    codeRunnersController.reset();
  }
}
