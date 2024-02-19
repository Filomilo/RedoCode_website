package com.redocode.backend.VmAcces.CodeRunners.Program;

import com.redocode.backend.Tools.StringFormatter;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Program.Factory.ProgramFactory;
import com.redocode.backend.VmAcces.CodeRunners.Variables.*;
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
            "#include <iostream>\n"+
            "int "+program.getInputGeneratorFunctionName()+"()\n" +
            "{\n" +
            "return "+var+";\n" +
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
                "#include <iostream>\n"+
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
                "#include <iostream>\n"+
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





    @ParameterizedTest
    @MethodSource("com.redocode.backend.ValuesProvider#singleFloatProvider")
    void getInputGeneratorCodeSingleFloat(Float var) {
        log.info("argemnt value: "+ var);
        SolutionProgram program=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setInputVaraiable(new SingleFloat(var))
                .build()
                ;
        String inputCodeGenerationExpected=
                "#include <iostream>\n"+
                "float "+program.getInputGeneratorFunctionName()+"()\n" +
                        "{\n" +
                        "return "+var+";\n" +
                        "}";
        assertEquals(inputCodeGenerationExpected,program.getInputGeneratorCode(),"inputArgs funciton doenst match one prepread in template");
    }



    @ParameterizedTest
    @MethodSource("com.redocode.backend.ValuesProvider#arrayFloatProvider")
    void getInputGeneratorCodeArrayFloat(Float[] var) {
        SolutionProgram program=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setInputVaraiable(new ArrayOfFloats(var))
                .build()
                ;
        String inputCodeGenerationExpected=
                "#include <iostream>\n"+
                "float* "+program.getInputGeneratorFunctionName()+"()\n" +
                        "{\n" +
                        "return new float["+var.length+"] {";

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
    @MethodSource("com.redocode.backend.ValuesProvider#doubleArrayFloatProvider")
    void getInputGeneratorCodeDoubleArrayFloat(Float[][] var) {
        log.info("var: "+ Arrays.deepToString(var));
        SolutionProgram program=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setInputVaraiable(new DoubleArrayOfFloats(var))
                .build()
                ;
        String inputCodeGenerationExpected=
                "#include <iostream>\n"+
                "float** "+program.getInputGeneratorFunctionName()+"()\n" +
                        "{\n" +
                        "float** arr = new float*["+var.length+"];\n";
        for (int i = 0; i <var.length ; i++) {
            inputCodeGenerationExpected+="arr["+i+"]"+" = new float["+var[0].length+"];\n";
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



    @ParameterizedTest
    @MethodSource("com.redocode.backend.ValuesProvider#singleStringProvider")
    void getInputGeneratorCodeSingleString(String var) {
        log.info("argemnt value: "+ var);
        SolutionProgram program=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setInputVaraiable(new SingleString(var))
                .build()
                ;
        String inputCodeGenerationExpected=
                "#include <iostream>\n"+
                "std::string "+program.getInputGeneratorFunctionName()+"()\n" +
                        "{\n" +
                        "return \""+var+"\";\n" +
                        "}";
        log.info("code: \n"+ inputCodeGenerationExpected);
        assertEquals(inputCodeGenerationExpected,program.getInputGeneratorCode(),"inputArgs funciton doenst match one prepread in template");
    }

    @ParameterizedTest
    @MethodSource("com.redocode.backend.ValuesProvider#arrayStringProvider")
    void getInputGeneratorCodeArrayString(String[] var) {
        log.info("val: "+Arrays.toString(var));
        SolutionProgram program=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setInputVaraiable(new ArrayOfStrings(var))
                .build()
                ;
        String inputCodeGenerationExpected=
                "#include <iostream>\n"+
                "std::string* "+program.getInputGeneratorFunctionName()+"()\n" +
                        "{\n" +
                        "return new std::string["+var.length+"] {";

        for (int i = 0; i < var.length; i++) {
            inputCodeGenerationExpected+="\""+StringFormatter.removeWhiteCharacterss(var[i])+"\"";
            if(i<var.length-1)
                inputCodeGenerationExpected+=", ";
        }
        inputCodeGenerationExpected+="};\n" +
                "}";
        log.info("code: \n"+inputCodeGenerationExpected);
        assertEquals(inputCodeGenerationExpected,program.getInputGeneratorCode(),"inputArgs funciton doenst match one prepread in template");
    }
    @ParameterizedTest
    @MethodSource("com.redocode.backend.ValuesProvider#doubleArrayStringProvider")
    void getInputGeneratorCodeDoubleArrayString(String[][] var) {
        log.info("var: "+ Arrays.deepToString(var));
        SolutionProgram program=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setInputVaraiable(new DoubleArrayOfStrings(var))
                .build()
                ;
        String inputCodeGenerationExpected=
                "#include <iostream>\n"+
                "std::string** "+program.getInputGeneratorFunctionName()+"()\n" +
                        "{\n" +
                        "std::string** arr = new std::string*["+var.length+"];\n";
        for (int i = 0; i <var.length ; i++) {
            inputCodeGenerationExpected+="arr["+i+"]"+" = new std::string["+var[0].length+"];\n";
        }
        for (int i = 0; i <var.length ; i++) {
            for (int j = 0; j < var[0].length; j++) {
                inputCodeGenerationExpected+="arr["+i+"]["+j+"]=\""+StringFormatter.removeWhiteCharacterss(var[i][j]) +"\";\n";
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