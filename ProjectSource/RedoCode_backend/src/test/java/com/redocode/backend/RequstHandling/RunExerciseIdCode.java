package com.redocode.backend.RequstHandling;

import com.redocode.backend.RequstHandling.Handlers.BaseRequestHandler;
import com.redocode.backend.RequstHandling.Requests.SingleDatabaseExerciseTestRequest;
import com.redocode.backend.Tools.RedoCodeObjectMapper;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import com.redocode.backend.database.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE.CPP_RUNNER;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RunExerciseIdCode {

  @Autowired ExerciseRepository exerciseRepository;

  @Autowired SolutionProgramsRepository solutionProgramsRepository;
  @Autowired ProgrammingLanguageRepository programmingLanguageRepository;
  @Autowired UsersRepository usersRepository;
  @Autowired ExerciseTestsRepository exerciseTestsRepository;
  Excersize excersize;

  @BeforeAll
  void createExerciseInDataBaseThatRreturnSingleIntInput() {

    //        exerciseTestsRepository.saveAll(tests);
    excersize =
        Excersize.builder()
            .excersizeName("TestExercise")
            .inputType(Variables.VARIABLES_TYPES.SINGLE_INTEGER)
            .outputType(Variables.VARIABLES_TYPES.SINGLE_INTEGER)
            .amountOfAutoTests(8)
            .author(usersRepository.findAll().get(0))
            .description("Test description")
            .maxExecutionTimeMS(1000L)
            .timeForTaskMin(50L)
            .valueLengthRangeMin(-100.0F)
            .valueLengthRangeMax(100.0F)
            .ram_mb(512)
            .build();
    exerciseRepository.save(excersize);

    List<ExerciseTests> tests = new ArrayList<>();
    tests.add(ExerciseTests.builder().expectedOutput("1").input("1").excersize(excersize).build());
    exerciseTestsRepository.saveAll(tests);

    solutionProgramsRepository.save(
        SolutionPrograms.builder()
            .code("int solution(int x){ return x;}")
            .language(
                programmingLanguageRepository.findByName(
                    RedoCodeObjectMapper.CodeRunnerToDataBaseLanguageName(CPP_RUNNER)))
            .excersize(excersize)
            .AvgExecutionTime(100L)
            .build());
    solutionProgramsRepository.save(
        SolutionPrograms.builder()
            .code("function solution(x){ return x;}")
            .language(
                programmingLanguageRepository.findByName(
                    RedoCodeObjectMapper.CodeRunnerToDataBaseLanguageName(
                        CODE_RUNNER_TYPE.JS_RUNNER)))
            .AvgExecutionTime(100L)
            .excersize(excersize)
            .build());
  }

  @ParameterizedTest
  @EnumSource(
      value = CODE_RUNNER_TYPE.class,
      mode = EnumSource.Mode.EXCLUDE,
      names = {"UNIDENTIFIED"})
  public void RunExerciseIdCodeCorrect(CODE_RUNNER_TYPE type) {
    User user = new User();
    long IdOfeExercise = exerciseRepository.findAll().get(0).getId();

    String solutionCode =
        solutionProgramsRepository
            .findFirstByLanguageIdAndExcersizeId(
                programmingLanguageRepository
                    .findByName(RedoCodeObjectMapper.CodeRunnerToDataBaseLanguageName(type))
                    .getId(),
                IdOfeExercise)
            .getCode();

    Map<CODE_RUNNER_TYPE, String> solutions = new HashMap<CODE_RUNNER_TYPE, String>();
    solutions.put(type, solutionCode);

    SingleDatabaseExerciseTestRequest singleDatabaseExerciseTestRequest =
        SingleDatabaseExerciseTestRequest.builder()
            .solutionCodes(solutions)
            .idOfExercise(IdOfeExercise)
            .user(user)
            .build();

    assertTrue(
        ResponsibilityChainRepository.runExerciseIdCode.next(singleDatabaseExerciseTestRequest));
  }

  @Test
  public void testRetruNTheSameCorect() {
    User user = new User();
    long IdOfeExercise = excersize.getId();

    String solutionCode =
        solutionProgramsRepository
            .findFirstByLanguageIdAndExcersizeId(
                programmingLanguageRepository
                    .findByName(RedoCodeObjectMapper.CodeRunnerToDataBaseLanguageName(CPP_RUNNER))
                    .getId(),
                IdOfeExercise)
            .getCode();

    Map<CODE_RUNNER_TYPE, String> solutions = new HashMap<CODE_RUNNER_TYPE, String>();
    solutions.put(CPP_RUNNER, solutionCode);

    SingleDatabaseExerciseTestRequest singleDatabaseExerciseTestRequest =
        SingleDatabaseExerciseTestRequest.builder()
            .solutionCodes(solutions)
            .idOfExercise(IdOfeExercise)
            .user(user)
            .build();

    BaseRequestHandler requestBase = ResponsibilityChainRepository.runExerciseIdCode;
    do {
      requestBase.set_continueOnError(false);

    } while ((requestBase = requestBase.getNextRequestHandler()) != null);

    assertTrue(
        ResponsibilityChainRepository.runExerciseIdCode.next(singleDatabaseExerciseTestRequest));
  }

  @Test
  public void testRetruSinelOneSoAutomaticTestShouldFail() {
    User user = new User();
    long IdOfeExercise = excersize.getId();

    String solutionCode =
        solutionProgramsRepository
            .findFirstByLanguageIdAndExcersizeId(
                programmingLanguageRepository
                    .findByName(RedoCodeObjectMapper.CodeRunnerToDataBaseLanguageName(CPP_RUNNER))
                    .getId(),
                IdOfeExercise)
            .getCode();

    Map<CODE_RUNNER_TYPE, String> solutions = new HashMap<CODE_RUNNER_TYPE, String>();
    solutions.put(CPP_RUNNER, "int solution(int x){ return 1;}");
    BaseRequestHandler requestBase = ResponsibilityChainRepository.runExerciseIdCode;
    do {
      requestBase.set_continueOnError(false);

    } while ((requestBase = requestBase.getNextRequestHandler()) != null);

    SingleDatabaseExerciseTestRequest singleDatabaseExerciseTestRequest =
        SingleDatabaseExerciseTestRequest.builder()
            .solutionCodes(solutions)
            .idOfExercise(IdOfeExercise)
            .user(user)
            .build();

    assertFalse(
        ResponsibilityChainRepository.runExerciseIdCode.next(singleDatabaseExerciseTestRequest));
  }
}
