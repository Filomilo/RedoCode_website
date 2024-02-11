package com.redocode.backend.coderunners.CodeRunners;

import com.redocode.backend.coderunners.CodeRunners.Program.Program;
import com.redocode.backend.coderunners.CodeRunners.Program.ProgramResult;
import com.redocode.backend.coderunners.CodeRunners.Variables.Variables;

public abstract class CodeRunner extends ContainerController {

    protected CodeRunner(String image) {
        super(image);
    }

    static enum CoderunnerTypes{
        CPP_RUNNER,
        JS_RUNNER
    }


    abstract String createProgramCodeFile(Program program);
    abstract String compileProgram(String sourceFile);
    abstract String getRunCommand(String programFile);
    void cleanup()
    {
        String[] files=listFiles();
        for (String file: files
             ) {
            removeFile(file);
        }
    }

    ProgramResult runProgram(Program program)
    {
        logger.info("running program: "+program.getProgramCode());
       String fileName= createProgramCodeFile(program);
       String programName= compileProgram(fileName);
       String runCommand=getRunCommand(programName);
        ConsoleOutput consoleOutput=executeBash(runCommand);
        Variables programOutput=null;
        if(program.getOutuputType()!=null)
        {
            logger.warn("reading file result is not implemented");
            // TODO: 11/02/2024 add reading file with result
        }
        cleanup();
        return new ProgramResult(consoleOutput,programOutput);
    }



}
