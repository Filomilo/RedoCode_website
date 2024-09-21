package com.redocode.backend.RequstHandling;

import com.redocode.backend.RequstHandling.Requests.SingleDatabaseExerciseTestRequest;
import com.redocode.backend.Tools.RedoCodeObjectMapper;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import com.redocode.backend.database.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE.CPP_RUNNER;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@Disabled("Not workign along isde other test")
class RunExerciseIdCodeSubmitChainTest {
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
    tests.add(
        ExerciseTests.builder().expectedOutput("155").input("155").excersize(excersize).build());
    tests.add(
        ExerciseTests.builder().expectedOutput("-155").input("-155").excersize(excersize).build());
    exerciseTestsRepository.saveAll(tests);

    solutionProgramsRepository.save(
        SolutionPrograms.builder()
            .code("int solution(int x){ return x;}")
            .language(
                programmingLanguageRepository.findByName(
                    RedoCodeObjectMapper.CodeRunnerToDataBaseLanguageName(CPP_RUNNER)))
            .excersize(excersize)
            .avgExecutionTime(100L)
                .date(new Date())
            .solutionAuthor(usersRepository.getReferenceById(1l))
            .build());
    solutionProgramsRepository.save(
        SolutionPrograms.builder()
            .code("function solution(x){ return x;}")
            .language(
                programmingLanguageRepository.findByName(
                    RedoCodeObjectMapper.CodeRunnerToDataBaseLanguageName(
                        CODE_RUNNER_TYPE.JS_RUNNER)))
            .excersize(excersize)
            .solutionAuthor(usersRepository.getReferenceById(1l))
            .avgExecutionTime(100L)
                .date(new Date())
            .build());
  }

  @ParameterizedTest
  @EnumSource(
      value = CODE_RUNNER_TYPE.class,
      mode = EnumSource.Mode.EXCLUDE,
      names = {"UNIDENTIFIED"})
  public void RunExerciseIdCodeCorrect(CODE_RUNNER_TYPE type) {
    User user = usersRepository.getReferenceById(1l);
    long IdOfeExercise = exerciseRepository.findAll().get(0).getId();
    long amountOfSolutionBefore = this.solutionProgramsRepository.count();

    String solutionCode =
        solutionProgramsRepository
                .findFirstByLanguageIdAndExcersizeId(
                    programmingLanguageRepository
                        .findByName(RedoCodeObjectMapper.CodeRunnerToDataBaseLanguageName(type))
                        .getId(),
                    IdOfeExercise)
                .getCode()
            + "\n";

    Map<CODE_RUNNER_TYPE, String> solutions = new HashMap<CODE_RUNNER_TYPE, String>();
    solutions.put(type, solutionCode);

    SingleDatabaseExerciseTestRequest singleDatabaseExerciseTestRequest =
        SingleDatabaseExerciseTestRequest.builder()
            .solutionCodes(solutions)
            .idOfExercise(IdOfeExercise)
            .user(user)
            .build();

    assertDoesNotThrow(
        () -> {
          ResponsibilityChainRepository.runExerciseIdCodeSubmit.startChain(
              singleDatabaseExerciseTestRequest);
        });

    long amountOfSolutionAfter = this.solutionProgramsRepository.count();
    assertEquals(amountOfSolutionBefore + 1, amountOfSolutionAfter);
    SolutionPrograms savedSolution = this.solutionProgramsRepository.findFirstByCode(solutionCode);
    assertNotNull(savedSolution);
    assertEquals(
        type, RedoCodeObjectMapper.LanguageNameToCodeRunner(savedSolution.getLanguage().getName()));
    assertTrue(savedSolution.getAvgExecutionTime() < 1000L);
  }
}
