package com.redocode.backend.VmAcces.CodeRunners;

import com.redocode.backend.Messages.CodeToRunMessage;
import com.redocode.backend.VmAcces.CodeRunners.Program.ProgramResult;
import com.redocode.backend.VmAcces.CodeRunnersController;
import lombok.extern.java.Log;
import org.checkerframework.checker.units.qual.C;
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
public class FromMessageProgramRun {


    @Autowired
    CodeRunnersController codeRunnersController;

    static CodeRunner cppCodeRunner;

    @BeforeAll
    static void initlizeCodeRunners()
    {
        cppCodeRunner=new CppCodeRunner();
        cppCodeRunner.start();
    }

    @AfterAll
    static void destroyCodeRunners()
    {
        cppCodeRunner.destroy();
    }

    @Test
    public void cppHelloWorldRun()
    {
    log.info("runnign cpp Hell World Test");

    String  helloWorldCode="#include <iostream>\n" +
            "\n" +
            "int main() {\n" +
            "    std::cout << \"Hello World!\";\n" +
            "    return 0;\n" +
            "}";
        CodeToRunMessage codeToRunMessage = CodeToRunMessage
                .builder()
                .code(helloWorldCode)
                .build();

        List<ProgramResult> resultList= codeRunnersController.
                runProgramFromMessage(cppCodeRunner,codeToRunMessage);
        log.info(" program hello world resutls: "+ Arrays.toString(resultList.toArray()));
        assertEquals("Hello World!",resultList.get(0).getConsoleOutput().getOutput());
    }



}
