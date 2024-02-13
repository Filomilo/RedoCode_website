package com.redocode.backend.VmAcces.CodeRunners;

import com.redocode.backend.VmAcces.CodeRunners.Program.ProgramResult;
import com.redocode.backend.VmAcces.CodeRunners.Program.RawProgram;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CppCodeRunnerTest {


    @Test
    void testCppHelloWorldRaw()
    {
        String code="#include <iostream>\n" +
                "\n" +
                "int main() {\n" +
                "    std::cout << \"Hello World!\";\n" +
                "    return 0;\n" +
                "}";
        RawProgram rawProgram=new RawProgram(code);
        CppCodeRunner cppCodeRunner=new CppCodeRunner();
        cppCodeRunner.start();
        ProgramResult programResult=cppCodeRunner.runProgram(rawProgram);
        cppCodeRunner.stop();
        cppCodeRunner.destroy();
        assertEquals("Hello World!",programResult.consoleOutput.output);


    }

}