package com.redocode.backend.VmAcces.CodeRunners.Program;

import com.redocode.backend.Excpetions.ContainerException;
import com.redocode.backend.Excpetions.VmControllerException;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunner;
import com.redocode.backend.VmAcces.CodeRunners.CppCodeRunner;
import com.redocode.backend.VmAcces.CodeRunners.JsCodeRunner;
import com.redocode.backend.VmAcces.CodeRunners.Program.Factory.ProgramFactory;
import com.redocode.backend.VmAcces.CodeRunners.Variables.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ContextConfiguration
// @Disabled("Islotating specific test for debugging")
public class JsSolutionProgramTest {

  @ParameterizedTest
  @MethodSource("com.redocode.backend.DataProviders.ValuesProvider#singleIntProvider")
  void runProgramJsReturnInt(Integer val) throws ContainerException, VmControllerException {
    log.info("Code runner cpp return");
    Program program =
        ProgramFactory.createSolutionProgram()
            .setSolutionCodeRunner(CODE_RUNNER_TYPE.JS_RUNNER)
            .setInputVaraiable(new SingleInteger(val))
            .setOutputBase(Variables.VARIABLES_TYPES.SINGLE_INTEGER)
            .setTimeout(500)
            .setSolutionCode("function solution(val){" + "return val;" + "}")
            .build();

    log.info("Code to run: \n\n\n" + program.getProgramCode() + "\n\n\n");

    CodeRunner codeRunner = new JsCodeRunner(128);
    codeRunner.start();
    ProgramResult result = codeRunner.runProgram(program);
    codeRunner.destroy();
    log.info("\n\ncode result: \n" + result);
    assertEquals(val, result.getVariables().getValue());
  }

  @ParameterizedTest
  @MethodSource("com.redocode.backend.DataProviders.ValuesProvider#singleFloatProvider")
  void runProgramJsReturnFloat(Float val) throws ContainerException, VmControllerException {
    log.info("Code runner cpp return");
    Program program =
        ProgramFactory.createSolutionProgram()
            .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
            .setInputVaraiable(new SingleFloat(val))
            .setOutputBase(Variables.VARIABLES_TYPES.SINGLE_FLOAT)
            .setTimeout(500)
            .setSolutionCode("float solution(float val){" + "return val;" + "}")
            .build();
    CodeRunner codeRunner = new CppCodeRunner(128);
    codeRunner.start();
    ProgramResult result = codeRunner.runProgram(program);
    codeRunner.destroy();
    log.info("\n\ncode result: \n" + result);
    assertEquals(val, result.getVariables().getValue());
  }

  @ParameterizedTest
  @MethodSource("com.redocode.backend.DataProviders.ValuesProvider#singleStringProvider")
  void runProgramJsReturnString(String val) throws ContainerException, VmControllerException {
    log.info("Code runner cpp return");
    Program program =
        ProgramFactory.createSolutionProgram()
            .setSolutionCodeRunner(CODE_RUNNER_TYPE.JS_RUNNER)
            .setInputVaraiable(new SingleString(val))
            .setOutputBase(Variables.VARIABLES_TYPES.SINGLE_STRING)
            .setTimeout(500)
            .setSolutionCode("function solution(val){" + "return val;" + "}")
            .build();
    CodeRunner codeRunner = new JsCodeRunner(128);
    codeRunner.start();
    ProgramResult result = codeRunner.runProgram(program);
    codeRunner.destroy();
    log.info("\n\ncode result: \n" + result);
    assertEquals(val, result.getVariables().getValue());
  }

  @ParameterizedTest
  @MethodSource("com.redocode.backend.DataProviders.ValuesProvider#arrayStringProvider")
  void runProgramJSReturnStringArray(String[] val)
      throws ContainerException, VmControllerException {
    log.info("Code runner cpp return");
    ArrayOfStrings resultTemplate = new ArrayOfStrings();
    resultTemplate.setW(val.length);
    Program program =
        ProgramFactory.createSolutionProgram()
            .setSolutionCodeRunner(CODE_RUNNER_TYPE.JS_RUNNER)
            .setInputVaraiable(new ArrayOfStrings(val))
            .setOutputBase(Variables.VARIABLES_TYPES.ARRAY_STRINGS)
            .setTimeout(500)
            .setSolutionCode("function solution(val){" + "return val;" + "}")
            .build();
    CodeRunner codeRunner = new JsCodeRunner(128);
    codeRunner.start();
    ProgramResult result = codeRunner.runProgram(program);
    codeRunner.destroy();
    log.info("\n\ncode result: \n" + result);
    assertArrayEquals((Object[]) val, (Object[]) result.getVariables().getValue());
  }

  @ParameterizedTest
  @MethodSource("com.redocode.backend.DataProviders.ValuesProvider#arrayIntProvider")
  void runProgramJsReturnIntArray(Integer[] val) throws ContainerException, VmControllerException {
    log.info("Code runner cpp return");
    ArrayOfIntegers resultTemplate = new ArrayOfIntegers();
    resultTemplate.setW(val.length);
    Program program =
        ProgramFactory.createSolutionProgram()
            .setSolutionCodeRunner(CODE_RUNNER_TYPE.JS_RUNNER)
            .setInputVaraiable(new ArrayOfIntegers(val))
            .setOutputBase(Variables.VARIABLES_TYPES.ARRAY_OF_INTEGERS)
            .setTimeout(500)
            .setSolutionCode("function solution(val){" + "return val;" + "}")
            .build();
    CodeRunner codeRunner = new JsCodeRunner(128);
    codeRunner.start();
    ProgramResult result = codeRunner.runProgram(program);
    codeRunner.destroy();
    log.info("\n\ncode result: \n" + result);
    assertArrayEquals((Object[]) val, (Object[]) result.getVariables().getValue());
  }

  //
  @ParameterizedTest
  @MethodSource("com.redocode.backend.DataProviders.ValuesProvider#arrayFloatProvider")
  void runProgramJsReturnIntArray(Float[] val) throws ContainerException, VmControllerException {
    log.info("Code runner cpp return");
    ArrayOfFloats resultTemplate = new ArrayOfFloats();
    resultTemplate.setW(val.length);
    Program program =
        ProgramFactory.createSolutionProgram()
            .setSolutionCodeRunner(CODE_RUNNER_TYPE.JS_RUNNER)
            .setInputVaraiable(new ArrayOfFloats(val))
            .setOutputBase(Variables.VARIABLES_TYPES.ARRAY_OF_FLOATS)
            .setTimeout(500)
            .setSolutionCode("function solution(val){" + "return val;" + "}")
            .build();
    CodeRunner codeRunner = new JsCodeRunner(128);
    codeRunner.start();
    ProgramResult result = codeRunner.runProgram(program);
    codeRunner.destroy();
    log.info("\n\ncode result: \n" + result);
    for (int i = 0; i < val.length; i++) {
      assertTrue(Math.abs(val[i] - ((Float[]) result.getVariables().getValue())[i]) < 0.001);
    }
  }

  //
  @ParameterizedTest
  @MethodSource("com.redocode.backend.DataProviders.ValuesProvider#doubleArrayStringProvider")
  void runProgramJsReturnStringDoubleArray(String[][] val)
      throws ContainerException, VmControllerException {
    log.info("Code runner cpp return");
    DoubleArrayOfStrings resultTemplate = new DoubleArrayOfStrings();
    resultTemplate.setW(val[0].length);
    resultTemplate.setH(val.length);
    Program program =
        ProgramFactory.createSolutionProgram()
            .setSolutionCodeRunner(CODE_RUNNER_TYPE.JS_RUNNER)
            .setInputVaraiable(new DoubleArrayOfStrings(val))
            .setOutputBase(Variables.VARIABLES_TYPES.DOUBLE_ARRAY_OF_STRINGS)
            .setTimeout(500)
            .setSolutionCode("function solution(val){" + "return val;" + "}")
            .build();
    CodeRunner codeRunner = new JsCodeRunner(128);
    codeRunner.start();
    ProgramResult result = codeRunner.runProgram(program);
    codeRunner.destroy();
    log.info("\n\ncode result: \n" + result);
    for (int h = 0; h < val.length; h++) {
      for (int w = 0; w < val[0].length; w++) {
        assertEquals(val[h][w], ((String[][]) result.getVariables().getValue())[h][w]);
      }
    }
  }

  //
  //
  @ParameterizedTest
  @MethodSource("com.redocode.backend.DataProviders.ValuesProvider#doubleArrayIntProvider")
  void runProgramJsReturnIntDoubleArray(Integer[][] val)
      throws ContainerException, VmControllerException {
    log.info("Code runner cpp return");
    DoubleArrayOfIntegers resultTemplate = new DoubleArrayOfIntegers();
    resultTemplate.setW(val[0].length);
    resultTemplate.setH(val.length);
    Program program =
        ProgramFactory.createSolutionProgram()
            .setSolutionCodeRunner(CODE_RUNNER_TYPE.JS_RUNNER)
            .setInputVaraiable(new DoubleArrayOfIntegers(val))
            .setTimeout(500)
            .setOutputBase(Variables.VARIABLES_TYPES.DOUBLE_ARRAY_OF_INTEGERS)
            .setSolutionCode("function solution(val){" + "return val;" + "}")
            .build();
    log.info("getProgramCode:\n\n\n" + program.getProgramCode());
    CodeRunner codeRunner = new JsCodeRunner(128);
    codeRunner.start();
    ProgramResult result = codeRunner.runProgram(program);
    codeRunner.destroy();
    log.info("\n\ncode result: \n" + result);
    for (int h = 0; h < val.length; h++) {
      for (int w = 0; w < val[0].length; w++) {

        assertEquals(val[h][w], ((Integer[][]) result.getVariables().getValue())[h][w]);
      }
    }
  }

  //
  //
  //
  @ParameterizedTest
  @MethodSource("com.redocode.backend.DataProviders.ValuesProvider#doubleArrayFloatProvider")
  void runProgramJsReturnFloatDoubleArray(Float[][] val)
      throws ContainerException, VmControllerException {
    log.info("Code runner cpp return");
    DoubleArrayOfFloats resultTemplate = new DoubleArrayOfFloats();
    resultTemplate.setW(val[0].length);
    resultTemplate.setH(val.length);
    Program program =
        ProgramFactory.createSolutionProgram()
            .setSolutionCodeRunner(CODE_RUNNER_TYPE.JS_RUNNER)
            .setInputVaraiable(new DoubleArrayOfFloats(val))
            .setOutputBase(Variables.VARIABLES_TYPES.DOUBLE_ARRAY_OF_FLOATS)
            .setTimeout(500)
            .setSolutionCode("function solution(val){" + "return val;" + "}")
            .build();
    CodeRunner codeRunner = new JsCodeRunner(128);
    codeRunner.start();
    ProgramResult result = codeRunner.runProgram(program);
    codeRunner.destroy();
    log.info("\n\ncode result: \n" + result);
    for (int h = 0; h < val.length; h++) {
      for (int w = 0; w < val[0].length; w++) {
        assertTrue(
            Math.abs(val[h][w] - ((Float[][]) result.getVariables().getValue())[h][w]) < 0.001);
      }
    }
  }
}
