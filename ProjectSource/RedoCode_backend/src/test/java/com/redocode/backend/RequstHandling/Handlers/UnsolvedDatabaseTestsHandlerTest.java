package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.RequstHandling.Requests.CodeRunnerRequest;
import com.redocode.backend.RequstHandling.Requests.SpecifiedSingleDatabaseExerciseTestRequest;
import com.redocode.backend.Tools.RedoCodeObjectMapper;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Variables.SingleInteger;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import com.redocode.backend.VmAcces.CodeRunnersController;
import com.redocode.backend.database.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class UnsolvedDatabaseTestsHandlerTest {
  @Autowired ExerciseRepository exerciseRepository;
  @Autowired UsersRepository usersRepository;
  @Autowired CodeRunnersController codeRunnersControlle;
  @Autowired private ProgrammingLanguageRepository programmingLanguageRepository;
  @Autowired private SolutionProgramsRepository solutionProgramsRepository;

  @AfterEach
  void clearCodeRunners() {
    codeRunnersControlle.reset();
  }

  @Test
  @SneakyThrows
  void handle() {
    User user = usersRepository.findAll().get(0);
    Excersize excersize =
        Excersize.builder()
            .ram_mb(128)
            .timeForTaskMin(5L)
            .author(user)
            .description("test")
            .valueLengthRangeMin(-50.0f)
            .valueLengthRangeMax(50f)
            .outputType(Variables.VARIABLES_TYPES.SINGLE_INTEGER)
            .inputType(Variables.VARIABLES_TYPES.SINGLE_INTEGER)
            .maxExecutionTimeMS(600L)
            .excersizeName("test")
            .build();
    exerciseRepository.save(excersize);

    SolutionPrograms solutionPrograms =
        SolutionPrograms.builder()
            .code("int solution(int x){return x+1;}")
            .language(programmingLanguageRepository.findByName("cpp"))
            .excersize(excersize)
            .avgExecutionTime(100L)
                .date(new Date())
            .solutionAuthor(usersRepository.getReferenceById(1l))
            .build();
    solutionProgramsRepository.save(solutionPrograms);

    List<ExerciseTests> exerciseTests = excersize.getExerciseTests();

    HashMap solutionCodes = new HashMap();
    solutionCodes.put(CODE_RUNNER_TYPE.CPP_RUNNER, "int solution(int x){return x+1;}");
    int[] arrayOfInputs = {1, 2, 3, 4, 5};
    int[] arrayOfExpectedOuputs = {2, 3, 4, 5, 6};

    List<ExerciseTests> tests = new ArrayList<>();
    for (int i = 0; i < arrayOfInputs.length; i++) {
      tests.add(
          ExerciseTests.builder()
              .input(RedoCodeObjectMapper.VarAsString(new SingleInteger(arrayOfInputs[i])))
              .expectedOutput(null)
              .build());
    }

    SpecifiedSingleDatabaseExerciseTestRequest specifiedSingleDatabaseExerciseTestRequest =
        (SpecifiedSingleDatabaseExerciseTestRequest)
            SpecifiedSingleDatabaseExerciseTestRequest.builder()
                .idOfExercise(excersize.getId())
                .user(user)
                .timeForExecution(1000L)
                .solutionCodes(solutionCodes)
                .inputType(Variables.VARIABLES_TYPES.SINGLE_INTEGER)
                .outputType(Variables.VARIABLES_TYPES.SINGLE_INTEGER)
                .build();
    specifiedSingleDatabaseExerciseTestRequest.setAutotestsToRun(tests);
    log.info("request: " + specifiedSingleDatabaseExerciseTestRequest);

    CodeRunnerAccesValidationHandler codeRunnerAccesValidationHandler =
        new CodeRunnerAccesValidationHandler();
    codeRunnerAccesValidationHandler.handle(
        CodeRunnerRequest.builder()
            .ram(256)
            .user(user)
            .codeRunnerType(CODE_RUNNER_TYPE.CPP_RUNNER)
            .build());

    UnsolvedDatabaseTestsHandler unsolvedDatabaseTests = new UnsolvedDatabaseTestsHandler();
    SpecifiedSingleDatabaseExerciseTestRequest resultingRequest =
        (SpecifiedSingleDatabaseExerciseTestRequest)
            unsolvedDatabaseTests.handle(specifiedSingleDatabaseExerciseTestRequest);

    log.info(
        "resultingRequest: " + Arrays.toString(resultingRequest.getAutotestsToRun().toArray()));

    for (int i = 0; i < arrayOfInputs.length; i++) {
      log.info("checking test: " + resultingRequest.getAutotestsToRun().get(i));
      int valueINputarrayOfInputs =
          (int)
              resultingRequest
                  .getAutotestsToRun()
                  .get(i)
                  .getParsedInput(Variables.VARIABLES_TYPES.SINGLE_INTEGER)
                  .getValue();

      assertNotNull(
          resultingRequest
              .getAutotestsToRun()
              .get(i)
              .getParsedOutput(Variables.VARIABLES_TYPES.SINGLE_INTEGER));
      assertEquals(
          valueINputarrayOfInputs + 1,
          resultingRequest
              .getAutotestsToRun()
              .get(i)
              .getParsedOutput(Variables.VARIABLES_TYPES.SINGLE_INTEGER)
              .getValue());
    }

    //        GetExerciseTestsFromDataBase getExerciseTestsFromDataBaseTest= new
    // GetExerciseTestsFromDataBase();
    //        singleDatabaseExerciseTestRequest.getIdOfExercise();
    //        CodeTestRequest codeTestRequest= (CodeTestRequest)
    // getExerciseTestsFromDataBaseTest.handle(singleDatabaseExerciseTestRequest);
    //        for (int i=0;i<exerciseTests.size();i++)
    //        {
    //
    // assertEquals(exerciseTests.get(i).getInput(),codeTestRequest.getTestsToRun().get(i).getInput());
    //
    // assertEquals(exerciseTests.get(i).getExpectedOutput(),codeTestRequest.getTestsToRun().get(i).getExpectedOutput());
    //        }

  }
}
