package com.redocode.backend.coderunners.CodeRunners;

import com.redocode.backend.coderunners.CodeRunners.Program.Program;

import java.util.Arrays;
import java.util.UUID;

public class CppCodeRunner extends CodeRunner{

    protected CppCodeRunner() {
        super("filipredocode/redocode:CppRunner");
    }

    @Override
    String createProgramCodeFile(Program program) {
        String fileName= UUID.randomUUID().toString();
        fileName+=".cpp";
        createFile(fileName,program.getProgramCode());
        return fileName;
    }

    @Override
    String compileProgram(String sourceFile) {
        String fileName= UUID.randomUUID().toString();
        String compileCommand="g++ -Wall "+sourceFile+" -o "+fileName;
        ConsoleOutput consoleOutput= executeBash(compileCommand);
        logger.info(consoleOutput.toString());
        return fileName;
    }

    @Override
    String getRunCommand(String programFile) {
        logger.info("List of files: \n"+Arrays.toString(listFiles()));
       return "./"+programFile;
    }


}
