package com.redocode.backend.VmAcces.CodeRunners.Program;

import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Program.Factory.ProgramFactory;
import com.redocode.backend.VmAcces.CodeRunners.Variables.ArrayOfIntegers;
import com.redocode.backend.VmAcces.CodeRunners.Variables.DoubleArrayOfIntegers;
import com.redocode.backend.VmAcces.CodeRunners.Variables.SingleInteger;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import com.redocode.backend.ValuesProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class CppSolutionProgramTest {


//    @Test
//    void getImports() {
//    }


    @ParameterizedTest
    @MethodSource("com.redocode.backend.ValuesProvider#singleIntProvider")
    void getInputGeneratorCodeSingleInt(Integer var) {
    log.info("argemnt value: "+ var);
        SolutionProgram program=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setInputVaraiable(new SingleInteger(var))
                .build()
                ;
    String inputCodeGenerationExpected=
            "int "+program.getInputGeneratorFunctionName()+"()\n" +
            "{\n" +
            "    return "+var+";\n" +
            "}";
        assertEquals(inputCodeGenerationExpected,program.getInputGeneratorCode(),"inputArgs funciton doenst match one prepread in template");
    }


    @ParameterizedTest
    @MethodSource("com.redocode.backend.ValuesProvider#arrayIntProvider")
    void getInputGeneratorCodeArrayInt(Integer[] var) {
        SolutionProgram program=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setInputVaraiable(new ArrayOfIntegers(var))
                .build()
                ;
        String inputCodeGenerationExpected=
                "int* "+program.getInputGeneratorFunctionName()+"()\n" +
                        "{\n" +
                        "return new int["+var.length+"] {";

        for (int i = 0; i < var.length; i++) {
            inputCodeGenerationExpected+=var[i];
            if(i<var.length-1)
                inputCodeGenerationExpected+=", ";
        }
        inputCodeGenerationExpected+="};\n" +
                        "}";
        log.info("code: \n"+inputCodeGenerationExpected);
        assertEquals(inputCodeGenerationExpected,program.getInputGeneratorCode(),"inputArgs funciton doenst match one prepread in template");
    }


    @ParameterizedTest
    @MethodSource("com.redocode.backend.ValuesProvider#doubleArrayIntProvider")
    void getInputGeneratorCodeDoubleArrayInt(Integer[][] var) {
        log.info("var: "+ Arrays.deepToString(var));
        SolutionProgram program=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setInputVaraiable(new DoubleArrayOfIntegers(var))
                .build()
                ;
        String inputCodeGenerationExpected=
                "int** "+program.getInputGeneratorFunctionName()+"()\n" +
                        "{\n" +
                        "int** arr = new int*["+var.length+"];\n";
        for (int i = 0; i <var.length ; i++) {
            inputCodeGenerationExpected+="arr["+i+"]"+" = new int["+var[0].length+"];\n";
        }
        for (int i = 0; i <var.length ; i++) {
            for (int j = 0; j < var[0].length; j++) {
                inputCodeGenerationExpected+="arr["+i+"]["+j+"]="+var[i][j]+";\n";
            }

        }
        inputCodeGenerationExpected+="return arr;\n}";
        log.info("code: \n"+inputCodeGenerationExpected);

        log.info("code: \n"+inputCodeGenerationExpected);
        assertEquals(inputCodeGenerationExpected,program.getInputGeneratorCode(),"inputArgs funciton doenst match one prepread in template");
    }


    @Test
    void getOutputGeneratorCode() {
    }

    @Test
    void getActivationFunction() {
    }
}