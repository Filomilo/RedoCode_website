package com.redocode.backend.VmAcces.CodeRunners;

import com.redocode.backend.VmAcces.CodeRunners.Program.ProgramResult;
import com.redocode.backend.VmAcces.CodeRunners.Program.RawProgram;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@Disabled("Islotating specific test for debugging")
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
        CppCodeRunner cppCodeRunner=new CppCodeRunner(128);
        cppCodeRunner.start();
        ProgramResult programResult=cppCodeRunner.runProgram(rawProgram);
        cppCodeRunner.stop();
        cppCodeRunner.destroy();
        assertEquals("Hello World!",programResult.getConsoleOutput().getOutput());
    }


    @Test
    void testCompileError()
    {
        String code="#include <iostream>\n" +
                "\n" +
                "int main() {\n" +
                "    std::coutga << \"Hello World!\";\n" +
                "    return 0;\n" +
                "}";
        RawProgram rawProgram=new RawProgram(code);
        CppCodeRunner cppCodeRunner=new CppCodeRunner(128);
        cppCodeRunner.start();
        ProgramResult programResult=cppCodeRunner.runProgram(rawProgram);
        cppCodeRunner.stop();
        cppCodeRunner.destroy();
        assertTrue(programResult.getConsoleOutput().getErrorOutput().contains(" error: 'coutga'"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "test2"})
    void provideStringAsArgument(String txt)
    {
        log.info("argument: "+ txt);

    }


}