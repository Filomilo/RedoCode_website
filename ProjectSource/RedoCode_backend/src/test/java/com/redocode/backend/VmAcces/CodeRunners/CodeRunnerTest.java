package com.redocode.backend.VmAcces.CodeRunners;

import com.redocode.backend.Excpetions.ContainerException;
import com.redocode.backend.Excpetions.VmControllerException;
import com.redocode.backend.VmAcces.CodeRunners.Program.Factory.ProgramFactory;
import com.redocode.backend.VmAcces.CodeRunners.Program.ProgramResult;
import com.redocode.backend.VmAcces.CodeRunners.Program.SolutionProgram;
import com.redocode.backend.VmAcces.CodeRunners.Variables.*;
import com.redocode.backend.VmAcces.CodeRunnersController;
import com.redocode.backend.database.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CodeRunnerTest {
  @Autowired CodeRunnersController codeRunnersController;
  CodeRunner jsrunner;

  @BeforeEach
  void initJSRUnner() throws ContainerException, VmControllerException {
    User user = User.builder().build();

    //
    // codeRunnersController.requestVm(CodeRunnerRequest.builder().codeRunnerType(CODE_RUNNER_TYPE.JS_RUNNER).ram(128).user(user).build());
    jsrunner = new JsCodeRunner(120);
    jsrunner.start();
  }

  @Test
  void ReturnInputVarInProgramResults() {
    SolutionProgram solutionProgram =
        ProgramFactory.createSolutionProgram()
            .setSolutionCodeRunner(CODE_RUNNER_TYPE.JS_RUNNER)
            .setSolutionCode("function solution(x){return x;}")
            .setOutputBase(Variables.VARIABLES_TYPES.SINGLE_INTEGER)
            .setInputVaraiable(new SingleInteger(1))
            .setTimeout(1000)
            .build();
    ProgramResult programResult = jsrunner.runProgram(solutionProgram);
    assertEquals(1, programResult.getVariables().getValue());
    assertEquals(1, programResult.getVariablesInput().getValue());
  }

  @AfterEach
  void destory() {
    jsrunner.stop();
    jsrunner.destroy();
  }
}
