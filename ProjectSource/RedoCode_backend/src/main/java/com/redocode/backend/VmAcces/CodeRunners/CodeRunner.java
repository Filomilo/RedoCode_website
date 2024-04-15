package com.redocode.backend.VmAcces.CodeRunners;

import com.redocode.backend.VmAcces.CodeRunners.Program.Program;
import com.redocode.backend.VmAcces.CodeRunners.Program.ProgramResult;
import com.redocode.backend.VmAcces.CodeRunners.Program.SolutionProgram;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import com.redocode.backend.VmAcces.CodeRunners.Variables.VariablesParser;
import javassist.compiler.ast.Variable;
import lombok.Getter;
import lombok.Synchronized;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class CodeRunner extends ContainerController {

    protected CodeRunner(String image) {
        super(image);
        type=CODE_RUNNER_TYPE.UUIANDTIFIED;
    }

    protected CODE_RUNNER_TYPE  type;



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
        try {
            logger.info("running program: " + program + "--------" + program.getProgramCode());
            String fileName = createProgramCodeFile(program);
            String programName = compileProgram(fileName);
            String runCommand = getRunCommand(programName);
            ConsoleOutput consoleOutput = executeBash(runCommand);
            Variables programOutput = null;
            if (program.getOutuputType() != null) {
                String resultFileContent=getFileContnt(((SolutionProgram)program).getOutputFileName());
                logger.info("Program outoput file: \n"+resultFileContent);
                programOutput= VariablesParser.parseVaraiables(program.getOutuputType(),resultFileContent);
            }
            cleanup();
            return new ProgramResult(consoleOutput, programOutput);
        }
        catch (Exception ex)
        {
            return ProgramResult.builder()
                    .consoleOutput(
                            ConsoleOutput.builder()
                                    .errorOutput(ex.getMessage())
                                    .build()
                    )
                    .build();
        }
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
