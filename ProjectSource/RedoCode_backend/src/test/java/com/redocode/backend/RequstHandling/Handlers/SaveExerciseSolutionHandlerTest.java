package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.RequstHandling.Requests.SaveExerciseSolutionRequest;
import com.redocode.backend.Tools.RedoCodeObjectMapper;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.ConsoleOutput;
import com.redocode.backend.VmAcces.CodeRunners.Program.ProgramResult;
import com.redocode.backend.VmAcces.CodeRunners.Variables.SingleInteger;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import com.redocode.backend.database.SolutionPrograms;
import com.redocode.backend.database.SolutionProgramsRepository;
import com.redocode.backend.database.User;
import com.redocode.backend.database.UsersRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class SaveExerciseSolutionHandlerTest {
  @Autowired UsersRepository usersRepository;
  @Autowired SolutionProgramsRepository solutionProgramsRepository;

  @Test
  @SneakyThrows
  void handle() {

    long amountOfSoultionsBefore = solutionProgramsRepository.count();

    User user = usersRepository.getReferenceById(1L);
    Long exerciseId = 1L;
    String solution = "Test solution";
    CODE_RUNNER_TYPE type = CODE_RUNNER_TYPE.CPP_RUNNER;
    Map<CODE_RUNNER_TYPE, String> solutions = new HashMap<CODE_RUNNER_TYPE, String>();
    solutions.put(type, solution);
    SaveExerciseSolutionHandler handler = new SaveExerciseSolutionHandler();
    Long[] ExecutionTimes = new Long[] {1L, 5L, 8L, 4L, 10L, 5L, 5L};
    ArrayList<ProgramResult> results = new ArrayList<>();
    for (Long executionTime : ExecutionTimes) {
      results.add(
          ProgramResult.builder()
              .variables(new SingleInteger(0))
              .variablesInput(new SingleInteger(0))
              .executionTime(executionTime)
              .consoleOutput(ConsoleOutput.builder().output("").errorOutput("").build())
              .build());
    }
    ;
    SaveExerciseSolutionRequest request =
        SaveExerciseSolutionRequest.builder()
            .programResults(results)
            .outputType(Variables.VARIABLES_TYPES.SINGLE_INTEGER)
            .user(user)
            .solutionCodes(solutions)
            .idOfExercise(exerciseId)
            .timeForExecution(500L)
            .inputType(Variables.VARIABLES_TYPES.SINGLE_INTEGER)
            .build();
    assertNotNull(handler.handle(request));
    long amountOfSoultionsAfter = solutionProgramsRepository.count();
    assertEquals(amountOfSoultionsBefore + 1, amountOfSoultionsAfter);
    SolutionPrograms solutionSaved = solutionProgramsRepository.findFirstByCode(solution);
    assertNotNull(solutionSaved);
    assertEquals(exerciseId, solutionSaved.getExcersize().getId());
    assertEquals(
        type, RedoCodeObjectMapper.LanguageNameToCodeRunner(solutionSaved.getLanguage().getName()));
    assertEquals(
        Math.ceil(Arrays.stream(ExecutionTimes).mapToLong(x -> x).average().orElse(Long.MIN_VALUE)),
        solutionSaved.getAvgExecutionTime().longValue());
  }
}
