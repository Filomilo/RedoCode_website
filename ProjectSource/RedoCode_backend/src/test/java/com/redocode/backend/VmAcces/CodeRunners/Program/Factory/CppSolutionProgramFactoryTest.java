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
public class CppSolutionProgramFactoryTest {


    @Autowired
    SolutionProgramsRepository solutionProgramsRepository;

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
                        "#include <vector>\n"+
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
                        "#include <vector>\n"+
                        "std::vector<int> "+program.getInputGeneratorFunctionName()+"()\n" +
                        "{\n" +
                        "return std::vector<int> {";

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
                        "#include <vector>\n"+
                        "std::vector<std::vector<int>> "+program.getInputGeneratorFunctionName()+"()\n" +
                        "{\n" +
                        "std::vector<std::vector<int>> arr;\n";
        for (int i = 0; i <var.length ; i++) {
            inputCodeGenerationExpected+="arr.push_back(std::vector<int>());\n";
        }
        for (int i = 0; i <var.length ; i++) {
            for (int j = 0; j < var[0].length; j++) {
                inputCodeGenerationExpected+="arr["+i+"].push_back("+var[i][j]+");\n";
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
                        "#include <vector>\n"+
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
                        "#include <vector>\n"+
                        "std::vector<float> "+program.getInputGeneratorFunctionName()+"()\n" +
                        "{\n" +
                        "return std::vector<float> {";

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
                        "#include <vector>\n"+
                        "std::vector<std::vector<float>> "+program.getInputGeneratorFunctionName()+"()\n" +
                        "{\n" +
                        "std::vector<std::vector<float>> arr;\n";
        for (int i = 0; i <var.length ; i++) {
            inputCodeGenerationExpected+="arr.push_back(std::vector<float>());\n";
        }
        for (int i = 0; i <var.length ; i++) {
            for (int j = 0; j < var[0].length; j++) {
                inputCodeGenerationExpected+="arr["+i+"].push_back("+var[i][j]+");\n";
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
                        "#include <vector>\n"+
                        "std::string "+program.getInputGeneratorFunctionName()+"()\n" +
                        "{\n" +
                        "return \""+ StringFormatter.removeWhiteCharacterss(var)+"\";\n" +
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
                        "#include <vector>\n"+
                        "std::vector<std::string> "+program.getInputGeneratorFunctionName()+"()\n" +
                        "{\n" +
                        "return std::vector<std::string> {";

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
                        "#include <vector>\n"+
                        "std::vector<std::vector<std::string>> "+program.getInputGeneratorFunctionName()+"()\n" +
                        "{\n" +
                        "std::vector<std::vector<std::string>> arr;\n";
        for (int i = 0; i <var.length ; i++) {
            inputCodeGenerationExpected+="arr.push_back(std::vector<std::string>());\n";
        }
        for (int i = 0; i <var.length ; i++) {
            for (int j = 0; j < var[0].length; j++) {
                inputCodeGenerationExpected+="arr["+i+"].push_back(\""+StringFormatter.removeWhiteCharacterss(var[i][j]) +"\");\n";
            }

        }
        inputCodeGenerationExpected+="return arr;\n}";
        log.info("code: \n"+inputCodeGenerationExpected);

        log.info("code: \n"+inputCodeGenerationExpected);
        assertEquals(inputCodeGenerationExpected,program.getInputGeneratorCode(),"inputArgs funciton doenst match one prepread in template");
    }

    @Test
    void getOutputGeneratorCodeSingleString() {


        SolutionProgram program=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setOutputBase(new SingleString())
                .build()
                ;


        String ouputGenerationCode=
                "#include <fstream>\n" +
                        "#include <iostream>\n" +
                        "#include <sstream>\n" +
                        "#include <vector>\n" +
                        "void "+program.getOutputGeneratorFunctionName()+"(std::string a)\n" +
                        "{\n" +
                        "std::ofstream myfile;\n" +
                        "myfile.open (\""+program.getOutputFileName()+"\");\n" +
                        "std::stringstream ss;\n" +
                        "ss<< a;\n" +
                        "std::string s=ss.str();\n" +
                        "for (size_t i = 0; i < s.size(); i++)\n" +
                        "{\n" +
                        "std::string str;\n" +
                        "switch (s[i])\n" +
                        "{\n" +
                        "case '\\\\':\n" +
                        "str=\"\\\\\\\\\";\n" +
                        "break;\n" +
                        "case '\\n':\n" +
                        "str=\"\\\\n\";\n" +
                        "break;\n" +
                        "case '\\t':\n" +
                        "str= \"\\\\t\";\n" +
                        "break;\n" +
                        "default:\n" +
                        "str=std::string(1,s[i]);\n" +
                        "};\n" +
                        "myfile << str;\n" +
                        "}\n" +
                        "myfile.close();\n" +
                        "}";
        assertEquals(ouputGenerationCode,program.getOutputGeneratorCode());

    }

    @Test
    void getOutputGeneratorCodeArrayString() {

        SolutionProgram program=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setOutputBase(new ArrayOfStrings())
                .build()
                ;


        String ouputGenerationCode=
                "#include <fstream>\n" +
                        "#include <iostream>\n" +
                        "#include <sstream>\n" +
                        "#include <vector>\n" +
                        "void "+program.getOutputGeneratorFunctionName()+"(std::vector<std::string> a)\n" +
                        "{\n" +
                        "std::ofstream myfile;\n" +
                        "myfile.open (\""+program.getOutputFileName()+"\");\n" +
                        "size_t l=a.size();\n" +
                        "for (size_t i = 0; i < l; i++)\n" +
                        "{\n" +
                        "std::stringstream ss;\n" +
                        "ss<< a[i];\n" +
                        "std::string s=ss.str();\n" +
                        "for (size_t j = 0; j < s.size(); j++)\n" +
                        "{\n" +
                        "std::string str;\n" +
                        "switch (s[j])\n" +
                        "{\n" +
                        "case '\\\\':\n" +
                        "str=\"\\\\\\\\\";\n" +
                        "break;\n" +
                        "case '\\n':\n" +
                        "str=\"\\\\n\";\n" +
                        "break;\n" +
                        "case '\\t':\n" +
                        "str= \"\\\\t\";\n" +
                        "break;\n" +
                        "default:\n" +
                        "str=std::string(1,s[j]);\n" +
                        "};\n" +
                        "myfile << str;\n" +
                        "}\n" +
                        "myfile <<\"\\t\";\n" +
                        "}\n" +
                        "myfile.close();\n" +
                        "}";

        log.info("Code: \n"+ ouputGenerationCode);
        assertEquals(ouputGenerationCode,program.getOutputGeneratorCode());
    }

    @Test
    void getOutputGeneratorCodeMultiArrayString() {

        SolutionProgram program=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setOutputBase(new DoubleArrayOfStrings())
                .build()
                ;

        String code =
                " void "+program.getOutputGeneratorFunctionName()+"(std::string** a)\n" +
                        "{\n" +
                        "std::ofstream myfile;\n" +
                        "myfile.open (\""+program.getOutputFileName()+"\");\n" +
                        "size_t w="+program.getOutput().getW()+";\n" +
                        "size_t h="+program.getOutput().getH()+";\n" +
                        "for (size_t i = 0; i < h; i++)\n" +
                        "{\n" +
                        "for (size_t j = 0; j < w; j++)\n" +
                        "{\n" +
                        "std::stringstream ss;\n" +
                        "ss<< a[i][j];\n" +
                        "std::string s=ss.str();\n" +
                        "for (size_t k = 0; k < s.size(); k++)\n" +
                        "{\n" +
                        "std::string str;\n" +
                        "switch (s[k])\n" +
                        "{\n" +
                        "case '\\\\':\n" +
                        "str=\"\\\\\\\\\";\n" +
                        "break;\n" +
                        "case '\\n':\n" +
                        "str=\"\\\\n\";\n" +
                        "break;\n" +
                        "case '\\t':\n" +
                        "str= \"\\\\t\";\n" +
                        "break;\n" +
                        "default:\n" +
                        "str=std::string(1,s[k]);\n" +
                        "};\n" +
                        "myfile << str;\n" +
                        "}\n" +
                        "myfile <<\"\\t\";\n" +
                        "}\n" +
                        "myfile <<\"\\n\";\n" +
                        "}\n" +
                        "myfile.close();\n" +
                        "}";
    }

    @Test
    void getActivationFunctionNOArumgument() {
        SolutionProgram program=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .build()
                ;

        String code=
                "int main()\n" +
                        "{\n" +
                        program.getOutputGeneratorFunctionName() +"(solution());\n" +
                        "return 0;\n" +
                        "}";
        log.info("Code: \n"+code);
        assertEquals(code,program.getActivationFunction());
    }

    @Test
    void getActivationFunctionSingleArumgument() {
        SolutionProgram program=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setInputVaraiable(new SingleInteger(1))
                .build()
                ;

        String code=
                "int main()\n" +
                        "{\n" +
                        program.getOutputGeneratorFunctionName() +"(solution("+program.getInputGeneratorFunctionName()+"()));\n" +
                        "return 0;\n" +
                        "}";
        log.info("code: \n"+ code);
        assertEquals(code,program.getActivationFunction());

    }

    @Test
    void getActivationFunctionArrayAsArumgument() {
        SolutionProgram program=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setInputVaraiable(new ArrayOfIntegers(new Integer[]{1, 8, 5, 4}))
                .build()
                ;

        String code=
                "int main()\n" +
                        "{\n" +
                        program.getOutputGeneratorFunctionName() +"(solution("+program.getInputGeneratorFunctionName()+"(),4));\n" +
                        "return 0;\n" +
                        "}";
        log.info("Code: \n"+code);
        assertEquals(code,program.getActivationFunction());
    }


    @Test
    void getActivationFunctionMultiArrayAsArumgument() {
        SolutionProgram program=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setInputVaraiable(new DoubleArrayOfIntegers(new Integer[][]{{1, 8, 5, 4},{1, 8, 5, 4}}))
                .build()
                ;

        String code=
                "int main()\n" +
                        "{\n" +
                        program.getOutputGeneratorFunctionName() +"(solution("+program.getInputGeneratorFunctionName()+"(),4,2));\n" +
                        "return 0;\n" +
                        "}";
        log.info("Code: \n"+code);
        assertEquals(code,program.getActivationFunction());
    }
    @Test
    void FibonachiCodeTest()
    {
        List<SolutionPrograms> list=solutionProgramsRepository.findAll();
        SolutionProgram solutionProgram=ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)// TODO: add class mapping data base string to Code runner enum
                .setInputVaraiable(new SingleInteger(1))
                .setOutputBase(new SingleInteger())
                .setSolutionCode(list.get(0).getCode())
                .build();
        String correctCode="int solutin(int val)\n" +
                "{\n" +
                "    int* arr=new int[val];\n" +
                "\n" +
                "if(val>=0)\n" +
                "  arr[0]=0;\n" +
                "if (val>=2)\n" +
                "   arr[1]=1;\n" +
                "for(int i=2;i<val;i++)\n" +
                "    {\n" +
                "        arr[i]=arr[i-1]+arr[i-2];\n" +
                "}\n" +
                "    return arr[val-1];\n" +
                "}\n" +
                "\n" +
                "\n" +
                "#include <iostream>\n" +
                "#include <vector>\n" +
                "int "+solutionProgram.getInputGeneratorFunctionName()+"()\n" +
                "{\n" +
                "return 1;\n" +
                "}\n" +
                "\n" +
                "#include <fstream>\n" +
                "#include <iostream>\n" +
                "#include <sstream>\n" +
                "#include <vector>\n" +
                "void "+solutionProgram.getOutputGeneratorFunctionName()+"(int a)\n" +
                "{\n" +
                "std::ofstream myfile;\n" +
                "myfile.open (\""+solutionProgram.getOutputFileName()+"\");\n" +
                "std::stringstream ss;\n" +
                "ss<< a;\n" +
                "std::string s=ss.str();\n" +
                "for (size_t i = 0; i < s.size(); i++)\n" +
                "{\n" +
                "std::string str;\n" +
                "switch (s[i])\n" +
                "{\n" +
                "case '\\\\':\n" +
                "str=\"\\\\\\\\\";\n" +
                "break;\n" +
                "case '\\n':\n" +
                "str=\"\\\\n\";\n" +
                "break;\n" +
                "case '\\t':\n" +
                "str= \"\\\\t\";\n" +
                "break;\n" +
                "default:\n" +
                "str=std::string(1,s[i]);\n" +
                "};\n" +
                "myfile << str;\n" +
                "}\n" +
                "myfile.close();\n" +
                "}\n" +
                "\n" +
                "int main()\n" +
                "{\n" +
                solutionProgram.getOutputGeneratorFunctionName()+"(solution("+solutionProgram.getInputGeneratorFunctionName()+"()));\n" +
                "return 0;\n" +
                "}";
        assertEquals(correctCode,solutionProgram.getProgramCode());
    }


}
