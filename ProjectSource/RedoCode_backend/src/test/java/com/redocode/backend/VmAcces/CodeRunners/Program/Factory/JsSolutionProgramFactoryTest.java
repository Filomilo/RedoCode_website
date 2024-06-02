package com.redocode.backend.VmAcces.CodeRunners.Program.Factory;

import com.redocode.backend.Tools.StringFormatter;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Program.SolutionProgram;
import com.redocode.backend.VmAcces.CodeRunners.Variables.*;
import com.redocode.backend.database.SolutionPrograms;
import com.redocode.backend.database.SolutionProgramsRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
@ContextConfiguration
public class JsSolutionProgramFactoryTest {

    @Autowired
    SolutionProgramsRepository solutionProgramsRepository;

    @ParameterizedTest
    @MethodSource("com.redocode.backend.ValuesProvider#singleIntProvider")
    void getInputGeneratorCodeSingleInt(Integer var) {
        log.info("argemnt value: "+ var);
        SolutionProgram program=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.JS_RUNNER)
                .setInputVaraiable(new SingleInteger(var))
                .build()
                ;
        String inputCodeGenerationExpected=
                        "function "+program.getInputGeneratorFunctionName()+"()\n" +
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
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.JS_RUNNER)
                .setInputVaraiable(new ArrayOfIntegers(var))
                .build()
                ;
        String inputCodeGenerationExpected=
                        "function "+program.getInputGeneratorFunctionName()+"()\n" +
                        "{\n" +
                        "return [";

        for (int i = 0; i < var.length; i++) {
            inputCodeGenerationExpected+=var[i];
            if(i<var.length-1)
                inputCodeGenerationExpected+=", ";
        }
        inputCodeGenerationExpected+="];\n" +
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
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.JS_RUNNER)
                .setInputVaraiable(new DoubleArrayOfIntegers(var))
                .build()
                ;
        String inputCodeGenerationExpected=
                        "function "+program.getInputGeneratorFunctionName()+"()\n" +
                        "{\n" +
                        "return [";
        for (int i = 0; i <var.length ; i++) {
            inputCodeGenerationExpected+="[";
            for (int j = 0; j < var[0].length; j++) {
                inputCodeGenerationExpected+=var[i][j];
                if(j<var[0].length-1)
                    inputCodeGenerationExpected+=", ";
            }
            inputCodeGenerationExpected+="]";
            if(i<var.length-1)
            inputCodeGenerationExpected+=", ";
        }
        inputCodeGenerationExpected+="]";
        inputCodeGenerationExpected+=";\n}";
        log.info("code: \n"+inputCodeGenerationExpected);

        log.info("code: \n"+inputCodeGenerationExpected);
        assertEquals(inputCodeGenerationExpected,program.getInputGeneratorCode(),"inputArgs funciton doenst match one prepread in template");
    }
//
//
//
//
//
    @ParameterizedTest
    @MethodSource("com.redocode.backend.ValuesProvider#singleFloatProvider")
    void getInputGeneratorCodeSingleFloat(Float var) {
        log.info("argemnt value: "+ var);
        SolutionProgram program=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.JS_RUNNER)
                .setInputVaraiable(new SingleFloat(var))
                .build()
                ;
        String inputCodeGenerationExpected=
                        "function "+program.getInputGeneratorFunctionName()+"()\n" +
                        "{\n" +
                        "return "+var+";\n" +
                        "}";
        assertEquals(inputCodeGenerationExpected,program.getInputGeneratorCode(),"inputArgs funciton doenst match one prepread in template");
    }
//
//
//
    @ParameterizedTest
    @MethodSource("com.redocode.backend.ValuesProvider#arrayFloatProvider")
    void getInputGeneratorCodeArrayFloat(Float[] var) {
        SolutionProgram program=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.JS_RUNNER)
                .setInputVaraiable(new ArrayOfFloats(var))
                .build()
                ;
        String inputCodeGenerationExpected=
                "function "+program.getInputGeneratorFunctionName()+"()\n" +
                        "{\n" +
                        "return [";

        for (int i = 0; i < var.length; i++) {
            inputCodeGenerationExpected+=var[i];
            if(i<var.length-1)
                inputCodeGenerationExpected+=", ";
        }
        inputCodeGenerationExpected+="];\n" +
                "}";
        log.info("code: \n"+inputCodeGenerationExpected);
        assertEquals(inputCodeGenerationExpected,program.getInputGeneratorCode(),"inputArgs funciton doenst match one prepread in template");
    }
//
//
    @ParameterizedTest
    @MethodSource("com.redocode.backend.ValuesProvider#doubleArrayFloatProvider")
    void getInputGeneratorCodeDoubleArrayFloat(Float[][] var) {
        log.info("var: "+ Arrays.deepToString(var));
        SolutionProgram program=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.JS_RUNNER)
                .setInputVaraiable(new DoubleArrayOfFloats(var))
                .build()
                ;
        String inputCodeGenerationExpected=
                        "function "+program.getInputGeneratorFunctionName()+"()\n" +
                                "{\n" +
                                "return [";
        for (int i = 0; i <var.length ; i++) {
            inputCodeGenerationExpected+="[";
            for (int j = 0; j < var[0].length; j++) {
                inputCodeGenerationExpected+=var[i][j];
                if(j<var[0].length-1)
                    inputCodeGenerationExpected+=", ";
            }
            inputCodeGenerationExpected+="]";
            if(i<var.length-1)
                inputCodeGenerationExpected+=", ";
        }
        inputCodeGenerationExpected+="]";
        inputCodeGenerationExpected+=";\n}";

        log.info("code: \n"+inputCodeGenerationExpected);
        assertEquals(inputCodeGenerationExpected,program.getInputGeneratorCode(),"inputArgs funciton doenst match one prepread in template");
    }
//
//
//
    @ParameterizedTest
    @MethodSource("com.redocode.backend.ValuesProvider#singleStringProvider")
    void getInputGeneratorCodeSingleString(String var) {
        log.info("argemnt value: "+ var);
        SolutionProgram program=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.JS_RUNNER)
                .setInputVaraiable(new SingleString(var))
                .build()
                ;
        String inputCodeGenerationExpected=
                        "function "+program.getInputGeneratorFunctionName()+"()\n" +
                        "{\n" +
                        "return \""+ StringFormatter.removeWhiteCharacterss(var)+"\";\n" +
                        "}";
        log.info("code: \n"+ inputCodeGenerationExpected);
        assertEquals(inputCodeGenerationExpected,program.getInputGeneratorCode(),"inputArgs funciton doenst match one prepread in template");
    }
//
    @ParameterizedTest
    @MethodSource("com.redocode.backend.ValuesProvider#arrayStringProvider")
    void getInputGeneratorCodeArrayString(String[] var) {
        log.info("val: "+Arrays.toString(var));
        SolutionProgram program=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.JS_RUNNER)
                .setInputVaraiable(new ArrayOfStrings(var))
                .build()
                ;
        String inputCodeGenerationExpected=
                        "function "+program.getInputGeneratorFunctionName()+"()\n" +
                        "{\n" +
                        "return [";

        for (int i = 0; i < var.length; i++) {
            inputCodeGenerationExpected+="\""+var[i]+"\"";
            if(i<var.length-1)
                inputCodeGenerationExpected+=", ";
        }
        inputCodeGenerationExpected+="];\n" +
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
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.JS_RUNNER)
                .setInputVaraiable(new DoubleArrayOfStrings(var))
                .build()
                ;
        String inputCodeGenerationExpected=
                "function "+program.getInputGeneratorFunctionName()+"()\n" +
                        "{\n" +
                        "return [";
        for (int i = 0; i <var.length ; i++) {
            inputCodeGenerationExpected+="[";
            for (int j = 0; j < var[0].length; j++) {
                inputCodeGenerationExpected+="\""+var[i][j]+"\"";
                if(j<var[0].length-1)
                    inputCodeGenerationExpected+=", ";
            }
            inputCodeGenerationExpected+="]";
            if(i<var.length-1)
                inputCodeGenerationExpected+=", ";
        }
        inputCodeGenerationExpected+="]";
        inputCodeGenerationExpected+=";\n}";

        log.info("code: \n"+inputCodeGenerationExpected);
        assertEquals(inputCodeGenerationExpected,program.getInputGeneratorCode(),"inputArgs funciton doenst match one prepread in template");
    }

    @Test
    void getOutputGeneratorCodeSingleString() {
        SolutionProgram program=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.JS_RUNNER)
                .setOutputBase(Variables.VARIABLES_TYPES.SINGLE_STRING)
                .build()
                ;
        String ouputGenerationCode=
                " const "+program.getOutputGeneratorFunctionName()+"_fs = require('fs');\n" +
                        "function "+program.getOutputGeneratorFunctionName()+"(a)\n" +
                        "{\n" +
                        "let str=\"err\";\n" +
                        "if(!Array.isArray(a)){\n" +
                        "str = a.toString().replace(/\\\\/g, \"\\\\\\\\\").replace(/\\n/g, \"\\\\n\").replace(/\\t/g, \"\\\\t\");\n" +
                        "}\n" +
                        "else{\n" +
                        "    if(!Array.isArray(a[0]))\n" +
                        "        {\n" +
                        "            a.forEach(element => {element.toString().replace(/\\\\/g, \"\\\\\\\\\").replace(/\\n/g, \"\\\\n\").replace(/\\t/g, \"\\\\t\")});\n" +
                        "            str=a.join(\"\\t\");\n" +
                        "        }\n" +
                        "        else{\n" +
                        "            a=a.map(element => {\n" +
                        "                element.forEach(x=>{x.toString().replace(/\\\\/g, \"\\\\\\\\\").replace(/\\n/g, \"\\\\n\").replace(/\\t/g, \"\\\\t\")})\n" +
                        "                return element= element.join(\"\\t\")\n" +
                        "            });\n" +
                        "            str=a.join(\"\\n\")\n" +
                        "        }\n" +
                        "}" +
                        program.getOutputGeneratorFunctionName()+"_fs.writeFileSync(\""+program.getOutputFileName()+"\",str);\n" +
                        "}";
        assertEquals(ouputGenerationCode,program.getOutputGeneratorCode());

    }
//
    @Test
    void getOutputGeneratorCodeArrayString() {

        SolutionProgram program=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.JS_RUNNER)
                .setOutputBase(Variables.VARIABLES_TYPES.ARRAY_STRINGS)
                .build()
                ;
       String ouputGenerationCode=
                " const "+program.getOutputGeneratorFunctionName()+"_fs = require('fs');\n" +
                        "function "+program.getOutputGeneratorFunctionName()+"(a)\n" +
                        "{\n" +
                        "let str=\"err\";\n" +
                        "if(!Array.isArray(a)){\n" +
                        "str = a.toString().replace(/\\\\/g, \"\\\\\\\\\").replace(/\\n/g, \"\\\\n\").replace(/\\t/g, \"\\\\t\");\n" +
                        "}\n" +
                        "else{\n" +
                        "    if(!Array.isArray(a[0]))\n" +
                        "        {\n" +
                        "            a.forEach(element => {element.toString().replace(/\\\\/g, \"\\\\\\\\\").replace(/\\n/g, \"\\\\n\").replace(/\\t/g, \"\\\\t\")});\n" +
                        "            str=a.join(\"\\t\");\n" +
                        "        }\n" +
                        "        else{\n" +
                        "            a=a.map(element => {\n" +
                        "                element.forEach(x=>{x.toString().replace(/\\\\/g, \"\\\\\\\\\").replace(/\\n/g, \"\\\\n\").replace(/\\t/g, \"\\\\t\")})\n" +
                        "                return element= element.join(\"\\t\")\n" +
                        "            });\n" +
                        "            str=a.join(\"\\n\")\n" +
                        "        }\n" +
                        "}" +
                        program.getOutputGeneratorFunctionName()+"_fs.writeFileSync(\""+program.getOutputFileName()+"\",str);\n" +
                        "}";

        log.info("Code: \n"+ ouputGenerationCode);
        assertEquals(ouputGenerationCode,program.getOutputGeneratorCode());
    }
//
    @Test
    void getOutputGeneratorCodeMultiArrayString() {

        SolutionProgram program=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.JS_RUNNER)
                .setOutputBase(Variables.VARIABLES_TYPES.ARRAY_STRINGS)
                .build()
                ;
        String ouputGenerationCode=
                " const "+program.getOutputGeneratorFunctionName()+"_fs = require('fs');\n" +
                        "function "+program.getOutputGeneratorFunctionName()+"(a)\n" +
                        "{\n" +
                        "let str=\"err\";\n" +
                        "if(!Array.isArray(a)){\n" +
                        "str = a.toString().replace(/\\\\/g, \"\\\\\\\\\").replace(/\\n/g, \"\\\\n\").replace(/\\t/g, \"\\\\t\");\n" +
                        "}\n" +
                        "else{\n" +
                        "    if(!Array.isArray(a[0]))\n" +
                        "        {\n" +
                        "            a.forEach(element => {element.toString().replace(/\\\\/g, \"\\\\\\\\\").replace(/\\n/g, \"\\\\n\").replace(/\\t/g, \"\\\\t\")});\n" +
                        "            str=a.join(\"\\t\");\n" +
                        "        }\n" +
                        "        else{\n" +
                        "            a=a.map(element => {\n" +
                        "                element.forEach(x=>{x.toString().replace(/\\\\/g, \"\\\\\\\\\").replace(/\\n/g, \"\\\\n\").replace(/\\t/g, \"\\\\t\")})\n" +
                        "                return element= element.join(\"\\t\")\n" +
                        "            });\n" +
                        "            str=a.join(\"\\n\")\n" +
                        "        }\n" +
                        "}" +
                        program.getOutputGeneratorFunctionName()+"_fs.writeFileSync(\""+program.getOutputFileName()+"\",str);\n" +
                        "}";

        log.info("Code: \n"+ ouputGenerationCode);
        assertEquals(ouputGenerationCode,program.getOutputGeneratorCode());
    }
//
    @Test
    void getActivationFunctionNOArumgument() {
        SolutionProgram program=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.JS_RUNNER)
                .build()
                ;

        String code= program.getOutputGeneratorFunctionName() +"(solution());";
        log.info("Code: \n"+code);
        assertEquals(code,program.getActivationFunction());
    }
//
    @Test
    void getActivationFunctionSingleArumgument() {
        SolutionProgram program=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.JS_RUNNER)
                .setInputVaraiable(new SingleInteger(1))
                .build()
                ;
        String code=program.getOutputGeneratorFunctionName() +"(solution("+program.getInputGeneratorFunctionName()+"()));";
        log.info("code: \n"+ code);
        assertEquals(code,program.getActivationFunction());
    }
//
    @Test
    void getActivationFunctionArrayAsArumgument() {
        SolutionProgram program=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.JS_RUNNER)
                .setInputVaraiable(new ArrayOfIntegers(new Integer[]{1, 8, 5, 4}))
                .build()
                ;

        String code=
                        program.getOutputGeneratorFunctionName() +"(solution("+program.getInputGeneratorFunctionName()+"()));";
        log.info("Code: \n"+code);
        assertEquals(code,program.getActivationFunction());
    }
//
//
    @Test
    void getActivationFunctionMultiArrayAsArumgument() {
        SolutionProgram program=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.JS_RUNNER)
                .setInputVaraiable(new DoubleArrayOfIntegers(new Integer[][]{{1, 8, 5, 4},{1, 8, 5, 4}}))
                .build()
                ;

        String code=
                        program.getOutputGeneratorFunctionName() +"(solution("+program.getInputGeneratorFunctionName()+"()));";
        log.info("Code: \n"+code);
        assertEquals(code,program.getActivationFunction());
    }
    @Test
    void FibonachiCodeTest()
    {
        List<SolutionPrograms> list=solutionProgramsRepository.findAll();
        SolutionProgram solutionProgram=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.JS_RUNNER)// TODO: add class mapping data base string to Code runner enum
                .setInputVaraiable(new SingleInteger(1))
                .setOutputBase(Variables.VARIABLES_TYPES.SINGLE_INTEGER)
                .setSolutionCode(list.get(1).getCode())
                .build();
        String correctCode="function solution(val) {\n" +
                "    let arr = new Array(val);\n" +
                "\n" +
                "    if (val >= 0)\n" +
                "        arr[0] = 0;\n" +
                "    if (val >= 2)\n" +
                "        arr[1] = 1;\n" +
                "for (let i = 2; i < val; i++) {\n" +
                "        arr[i] = arr[i - 1] + arr[i - 2];\n" +
                "}\n" +
                "    return arr[val - 1];\n" +
                "}\n" +
                "\n" +
                "\n" +
                "function "+solutionProgram.getInputGeneratorFunctionName()+"()\n" +
                "{\n" +
                "return 1;\n" +
                "}\n" +
                "\n" +
                " const "+solutionProgram.getOutputGeneratorFunctionName()+"_fs = require('fs');\n" +
                "function "+solutionProgram.getOutputGeneratorFunctionName()+"(a)\n" +
                "{\n" +
                "let str=\"err\";\n" +
                "if(!Array.isArray(a)){\n" +
                "str = a.toString().replace(/\\\\/g, \"\\\\\\\\\").replace(/\\n/g, \"\\\\n\").replace(/\\t/g, \"\\\\t\");\n" +
                "}\n" +
                "else{\n" +
                "    if(!Array.isArray(a[0]))\n" +
                "        {\n" +
                "            a.forEach(element => {element.toString().replace(/\\\\/g, \"\\\\\\\\\").replace(/\\n/g, \"\\\\n\").replace(/\\t/g, \"\\\\t\")});\n" +
                "            str=a.join(\"\\t\");\n" +
                "        }\n" +
                "        else{\n" +
                "            a=a.map(element => {\n" +
                "                element.forEach(x=>{x.toString().replace(/\\\\/g, \"\\\\\\\\\").replace(/\\n/g, \"\\\\n\").replace(/\\t/g, \"\\\\t\")})\n" +
                "                return element= element.join(\"\\t\")\n" +
                "            });\n" +
                "            str=a.join(\"\\n\")\n" +
                "        }\n" +
                "}" +
                solutionProgram.getOutputGeneratorFunctionName()+"_fs.writeFileSync(\""+solutionProgram.getOutputFileName()+"\",str);\n" +
                "}\n" +
                "\n" +
                solutionProgram.getOutputGeneratorFunctionName()+"(solution("+solutionProgram.getInputGeneratorFunctionName()+"()));";
        assertEquals(correctCode,solutionProgram.getProgramCode());
    }
}
