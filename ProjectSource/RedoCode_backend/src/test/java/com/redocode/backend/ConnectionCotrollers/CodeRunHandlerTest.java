package com.redocode.backend.ConnectionCotrollers;

import com.redocode.backend.Messages.CodeRunningMessages.ExerciseCreatorValidationMessage;
import com.redocode.backend.Messages.UtilContainers.InputSize;
import com.redocode.backend.Messages.UtilContainers.Range;
import com.redocode.backend.RedoCodeController;
import com.redocode.backend.StompPrincipal;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import com.redocode.backend.VmAcces.CodeRunnersController;
import com.redocode.backend.database.*;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.security.Principal;
import java.sql.Time;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ContextConfiguration
@Disabled("Islotating specific test for debugging")
class CodeRunHandlerTest {
@Autowired
    CodeRunHandler codeRunHandler;
@Autowired
    ExerciseRepository exerciseRepository;
@Autowired
    RedoCodeController redoCodeController;
@Autowired
    UsersRepository usersRepository;
@Autowired
    CodeRunnersController codeRunnersController;
    @Test
    void runExerciseIdCode() {
        //todo: runExerciseIdCode
    }

    @Test
    void runRawCode() {
        //todo: runRawCode
    }

    @Test
    void runExercsieIdValidationCode() {
        //todo: runExercsieIdValidationCode
    }

    @Test
    void runExercsieTestsCode() {
        //todo: runExercsieTestsCode
    }

    @Test
    void runExerciseCreatorValidationCodeCorrect() {
        String userSessionId="sessionID";
        Long userId=1L;
;        Variables.VARIABLES_TYPES inputType= Variables.VARIABLES_TYPES.DOUBLE_ARRAY_OF_STRINGS;
        Variables.VARIABLES_TYPES ouptutType= Variables.VARIABLES_TYPES.DOUBLE_ARRAY_OF_STRINGS;
        int ram=1024;
        String title="Exercise";
        String decritpion="Descritpion";

        int amountOfAutoTests=8;
        boolean breakCharacterInput=true;
        Range lengthRange=new Range(0F,20F);
        boolean lowerCaseInput=true;
        boolean numberInput=true;
        boolean spaceInput=true;
        boolean specialCharacterInput=true;
        boolean upperCaseInput=true;
        Range xArrayRange= new Range(1F,20F);
        Range yArrayRange=new Range(1F,20F);
        long timeForTask= 60L;
        int amountOfAutoTask=8;

        Long maxExecutionTimeMS=1000L;

        HashMap<CODE_RUNNER_TYPE,String> solutionCodes=new HashMap<>()
        {{
            put (CODE_RUNNER_TYPE.CPP_RUNNER,"#include <iostream>\n" +
                    "#include <vector>\n" +
                    "#include <string>\n" +
                    "\n" +
                    "std::vector<std::vector<std::string>> solution(std::vector<std::vector<std::string>> in)\n" +
                    "{\n" +
                    "    return in;\n" +
                    "}");
            put(CODE_RUNNER_TYPE.JS_RUNNER,"function solution(array){return array;}");
        }};
        ExerciseTests[] tests= new ExerciseTests[]{
                ExerciseTests.builder()
                        .expectedOutput("[[\"1\",\"2\"],[\"3\",\"4\"],[\"5\",\"6\"]]")
                        .input("[[\"1\",\"2\"],[\"3\",\"4\"],[\"5\",\"6\"]]")
                        .excersize(null)
                        .build(),
                ExerciseTests.builder()
                        .expectedOutput("[[\"1\",\"2\"],[\"3\",\"4\"],[\"5\",\"6\"],[\"1\",\"2\"],[\"3\",\"4\"],[\"5\",\"6\"]]")
                        .input("[[\"1\",\"2\"],[\"3\",\"4\"],[\"5\",\"6\"],[\"1\",\"2\"],[\"3\",\"4\"],[\"5\",\"6\"]]")
                        .excersize(null)
                        .build(),

        };


        int amountOfExeciseBeforeAdding=exerciseRepository.findAll().size();



        ExerciseCreatorValidationMessage creatorValidationMessage=
                ExerciseCreatorValidationMessage.builder()
         .title(title)
        .description(decritpion)
        .inputType(inputType)
        .outputType(ouptutType)
        .amountOfAutoTests(amountOfAutoTests)
        .lengthRange(lengthRange)
        .xArrayRange(xArrayRange)
        .yArrayRange(yArrayRange)
        .upperCaseInput(upperCaseInput)
        .lowerCaseInput(lowerCaseInput)
        .numberInput(numberInput)
        .specialCharacterInput(specialCharacterInput)
        .breakCharacterInupt(breakCharacterInput)
        .spaceInupt(spaceInput)
        .timeForTaskMin(timeForTask)
        .timeForExecutionMs(maxExecutionTimeMS)
        .solutionCodes(solutionCodes)
        .manualTests(Arrays.stream(tests).toList())

        .ram(ram)
                        .build();


        User user= usersRepository.getReferenceById(userId);
        user.setSessionID(userSessionId);
        StompPrincipal principal=new StompPrincipal(userSessionId);
        redoCodeController.addConnectedUser(user);

        codeRunHandler.runExerciseCreatorValidationCode(principal,creatorValidationMessage);
        int amountOfExeciseAfterdding=exerciseRepository.findAll().size();


        Excersize lastAdded=exerciseRepository.findAll().get(exerciseRepository.findAll().size()-1);
        assertEquals(amountOfExeciseBeforeAdding+1,amountOfExeciseAfterdding,"no new exercsie was added");
        assertEquals(title,lastAdded.getExcersizeName());
        assertEquals(inputType,lastAdded.getInputType());
        assertEquals(ouptutType,lastAdded.getOutputType());
        assertEquals(ram,lastAdded.getRam_mb());
        assertEquals(decritpion,lastAdded.getDescription());
        assertEquals(userId,lastAdded.getAuthor().getId());
        assertEquals(Arrays.stream(tests).toList(),lastAdded.getExerciseTests().stream().toList());


        assertEquals(breakCharacterInput,lastAdded.getBreakCharacterInput());
        assertEquals(lowerCaseInput,lastAdded.getLowerCaseInput());
        assertEquals(breakCharacterInput,lastAdded.getLowerCaseInput());
        assertEquals(numberInput,lastAdded.getNumberInput());
        assertEquals(spaceInput,lastAdded.getSpaceInput());
        assertEquals(specialCharacterInput,lastAdded.getSpecialCharacterInput());
        assertEquals(upperCaseInput,lastAdded.getUpperCaseInput());

        assertEquals(lengthRange,lastAdded.getLengthRange());
        assertEquals(xArrayRange,lastAdded.getXArrayRange());
        assertEquals(yArrayRange,lastAdded.getYArrayRange());

        assertEquals(amountOfAutoTests,lastAdded.getAmountOfAutoTests());
        assertEquals(timeForTask,lastAdded.getTimeForTaskMin());

        assertEquals(maxExecutionTimeMS,lastAdded.getMaxExecutionTimeMS());

    }

    @Test
    void testRunExerciseIdCode() {
    }
}