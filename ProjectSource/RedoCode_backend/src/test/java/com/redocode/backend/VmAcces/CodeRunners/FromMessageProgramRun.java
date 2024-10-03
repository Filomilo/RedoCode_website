package com.redocode.backend.VmAcces.CodeRunners;

import com.redocode.backend.Excpetions.ContainerException;
import com.redocode.backend.Messages.CodeRunningMessages.ExerciseIdToRunMessage;
import com.redocode.backend.VmAcces.CodeRunners.Program.ProgramResult;
import com.redocode.backend.VmAcces.CodeRunnersController;
import lombok.extern.java.Log;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration
@Log
// @Disabled("Islotating specific test for debugging")
public class FromMessageProgramRun {

  @Autowired CodeRunnersController codeRunnersController;

  static CodeRunner cppCodeRunner;

  @BeforeAll
  static void initlizeCodeRunners() throws ContainerException {
    cppCodeRunner = new CppCodeRunner(128);
    cppCodeRunner.start();
  }

  @AfterAll
  static void destroyCodeRunners() {
    cppCodeRunner.destroy();
  }

  @Test
  public void cppHelloWorldRun() {
    log.info("runnign cpp Hell World Test");

    String helloWorldCode =
        "#include <iostream>\n"
            + "\n"
            + "int main() {\n"
            + "    std::cout << \"Hello World!\";\n"
            + "    return 0;\n"
            + "}";
    ExerciseIdToRunMessage exerciseIdToRunMessage =
        ExerciseIdToRunMessage.builder().code(helloWorldCode).build();

    List<ProgramResult> resultList =
        codeRunnersController.runProgramFromMessage(cppCodeRunner, exerciseIdToRunMessage);
    log.info(" program hello world resutls: " + Arrays.toString(resultList.toArray()));
    assertEquals("Hello World!", resultList.get(0).getConsoleOutput().getOutput());
  }

  @Test
  public void cppFibianchiTestRun() {
    log.info("runnign cpp Fibonachi nubmer test");

    String anwserCode =
        "int solution(int n) {\n"
            + "    if (n <= 2)\n"
            + "        return n-1;\n"
            + "    return solution(n - 1) + solution(n - 2);\n"
            + "}";

    ExerciseIdToRunMessage exerciseIdToRunMessage =
        ExerciseIdToRunMessage.builder().code(anwserCode).exercise_id(1L).build();

    List<ProgramResult> resultList =
        codeRunnersController.runProgramFromMessage(cppCodeRunner, exerciseIdToRunMessage);
    log.info(" program fibonachi resutls: " + Arrays.toString(resultList.toArray()));
    //        assertEquals("Hello World!",resultList.get(0).getConsoleOutput().getOutput());
  }
}
