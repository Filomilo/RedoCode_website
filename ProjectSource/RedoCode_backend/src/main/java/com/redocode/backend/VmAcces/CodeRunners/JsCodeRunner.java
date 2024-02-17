package com.redocode.backend.VmAcces.CodeRunners;

import com.redocode.backend.VmAcces.CodeRunners.Program.Program;

import java.util.Arrays;
import java.util.UUID;

public class JsCodeRunner extends CodeRunner{

    protected JsCodeRunner() {
        super("filipredocode/redocode:JsRunner");
    }

    @Override
    String createProgramCodeFile(Program program) {
        String fileName= UUID.randomUUID().toString();
        fileName+=".js";
        createFile(fileName,program.getProgramCode());
        return fileName;
    }

    @Override
    String compileProgram(String sourceFile) {
       return sourceFile;
    }

    @Override
    String getRunCommand(String programFile) {
        logger.info("List of files: \n"+ Arrays.toString(listFiles()));
        return "node ./"+programFile;
    }
}
