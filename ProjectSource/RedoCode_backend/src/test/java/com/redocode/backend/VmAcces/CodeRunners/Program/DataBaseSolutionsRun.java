package com.redocode.backend.VmAcces.CodeRunners.Program;

import com.redocode.backend.VmAcces.CodeRunners.CodeRunner;
import com.redocode.backend.VmAcces.CodeRunners.CppCodeRunner;
import com.redocode.backend.VmAcces.CodeRunners.JsCodeRunner;
import com.redocode.backend.VmAcces.CodeRunners.Program.Factory.ProgramFactory;
import com.redocode.backend.VmAcces.CodeRunners.Variables.SingleInteger;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import com.redocode.backend.database.*;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.lang.model.type.UnknownTypeException;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ContextConfiguration
public class DataBaseSolutionsRun {
    @Autowired
    SolutionProgramsRepository solutionProgramsRepository;
    @Autowired
    ExerciseRepository exerciseRepository;

    @Test
    public void rundatabaseExercises()
    {
        assertDoesNotThrow(()->{
            List<Excersize> list=exerciseRepository.findAll();
            for (Excersize ex:list
            ) {
                log.info("exeriswe: "+ ex);

                Set<SolutionPrograms> solutions=ex.getSolutions();
                for (SolutionPrograms sol:solutions
                ) {
                    log.info("SOlutions: "+ sol.toString());
                    CodeRunner codeRunner=switch (sol.getLanguage().getName()){
                        case "cpp" -> new CppCodeRunner(128);
                        case "js" -> new JsCodeRunner(128);
                        default -> throw new Exception("Unkown code unner type");
                    };
                    codeRunner.start();
                    List<ExerciseTests> tests=ex.getExerciseTests();
                    try {
                        for (ExerciseTests test : tests
                        ) {
                            log.info("test: " + test);
                            SolutionProgram program =
                                    ProgramFactory.createSolutionProgram()
                                            .setSolutionCodeRunner(codeRunner.getType())
                                            .setSolutionCode(sol.getCode())
                                            .setOutputBase(Variables.VARIABLES_TYPES.SINGLE_INTEGER) // todo: mapping type from data base to Varaible type
                                            .setInputVaraiable(test.getParsedInput(Variables.VARIABLES_TYPES.SINGLE_INTEGER)) // todo: Dynamic mapping for type
                                            .build();
                            ProgramResult result = codeRunner.runProgram(program);
                            assertEquals(test.getParsedOutput(Variables.VARIABLES_TYPES.SINGLE_INTEGER), result.getVariables());
                            log.info("\n\n\n\nReuslt: " + result);


                        }
                    }
                    finally {
                        codeRunner.destroy();
                    }



                }
            }
        });

    }


}
