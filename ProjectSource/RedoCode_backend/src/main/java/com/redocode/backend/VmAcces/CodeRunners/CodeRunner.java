package com.redocode.backend.VmAcces.CodeRunners;

import com.redocode.backend.VmAcces.CodeRunners.Program.Program;
import com.redocode.backend.VmAcces.CodeRunners.Program.ProgramResult;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import javassist.compiler.ast.Variable;
import lombok.Synchronized;

import java.util.ArrayList;
import java.util.List;

public abstract class CodeRunner extends ContainerController {

    protected CodeRunner(String image) {
        super(image);
    }

    public enum CoderunnerTypes{
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

    @Synchronized
    public ProgramResult runProgram(Program program)
    {
        logger.info("running program: "+program+"--------"+program.getProgramCode());
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

    public ProgramResult runProgram(Program program, Variable variablesInput)
    {
        return null;
    }
    public List<ProgramResult> runProgram(Program program, List<Variable> variablesInput)
    {
        List<ProgramResult> results=new ArrayList<>();
        if(variablesInput.size()==0)
        {
            results.add(this.runProgram(program));
        }
        else {
            for (Variable var: variablesInput
                 ) {
                results.add(this.runProgram(program,var));
            }
        }
        return results;
    }

}
