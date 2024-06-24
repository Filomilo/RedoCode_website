package com.redocode.backend.RequstHandling;

import com.redocode.backend.Messages.UtilContainers.Range;
import com.redocode.backend.RequstHandling.Requests.ExerciseCreationRequest;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import com.redocode.backend.VmAcces.CodeRunnersController;
import com.redocode.backend.database.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.sql.Time;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ContextConfiguration
class ResponsibilityChainRepositoryTest {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    CodeRunnersController codeRunnersController;
    @Autowired
    ExerciseRepository exerciseRepository;
    User user;

    @BeforeEach
    void setupUser()
    {
        user=new User("uuid","nick", User.USER_TYPE.PREMIUM);
        usersRepository.save(user);

    }





    @Test
    void testCoreectExerciseCreation()
    {
        Variables.VARIABLES_TYPES inputType= Variables.VARIABLES_TYPES.DOUBLE_ARRAY_OF_STRINGS;
        Variables.VARIABLES_TYPES ouptutType= Variables.VARIABLES_TYPES.DOUBLE_ARRAY_OF_STRINGS;
        int ram=1024;
        String title="Exercise";
        String decritpion="Descritpion";

        int amountOfAutoTests=8;
        boolean breakCharacterInput=true;
        Range lengthRange=new Range(0F,100F);
        boolean lowerCaseInput=true;
        boolean numberInput=true;
        boolean spaceInput=true;
        boolean specialCharacterInput=true;
        boolean upperCaseInput=true;
        Range xArrayRange= new Range(1F,20F);
        Range yArrayRange=new Range(1F,20F);
        Time timeForTask= new Time(20000);
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
                        .expectedOutput("{\"value\": [[\"1\",\"2\"],[\"3\",\"4\"],[\"5\",\"6\"]]}")
                        .input("{\"value\": [[\"1\",\"2\"],[\"3\",\"4\"],[\"5\",\"6\"]]}")
                        .excersize(null)
                        .build(),
               ExerciseTests.builder()
                       .expectedOutput("{\"value\": [[\"1\",\"2\"],[\"3\",\"4\"],[\"5\",\"6\"],[\"1\",\"2\"],[\"3\",\"4\"],[\"5\",\"6\"]]}")
                       .input("{\"value\": [[\"1\",\"2\"],[\"3\",\"4\"],[\"5\",\"6\"],[\"1\",\"2\"],[\"3\",\"4\"],[\"5\",\"6\"]]}")
                       .excersize(null)
                       .build(),

        };


        ExerciseCreationRequest exerciseCreationRequest= ExerciseCreationRequest.builder()
                .user(user)
                .outputType(ouptutType)
                .inputType(inputType)
                .ram(ram)
                .Title(title)
                .Description(decritpion)
                .solutionCodes(solutionCodes)
                .autoTestAmount(amountOfAutoTask)
                .amountOfAutoTests(amountOfAutoTests)
                .breakCharacterInput(breakCharacterInput)
                .lengthRange(lengthRange)
                .lowerCaseInput(lowerCaseInput)
                .numberInput(numberInput)
                .spaceInput(spaceInput)
                .specialCharacterInput(specialCharacterInput)
                .upperCaseInput(upperCaseInput)
                .xArrayRange(xArrayRange)
                .yArrayRange(yArrayRange)
                .testsToRun(tests)
                .timeForTask(timeForTask)
                .timeForExecution(maxExecutionTimeMS)
                .build();

assertDoesNotThrow(
        ()->{
            assertTrue(ResponsibilityChainRepository.createNewExercise.next(exerciseCreationRequest));
        }
);


        Excersize lastAdded=exerciseRepository.findAll().get(exerciseRepository.findAll().size()-1);
        assertEquals(title,lastAdded.getExcersizeName());
        assertEquals(inputType,lastAdded.getInputType());
        assertEquals(ouptutType,lastAdded.getOutputType());
        assertEquals(ram,lastAdded.getRam_mb());
        assertEquals(decritpion,lastAdded.getDescription());
        assertEquals(user.getId(),lastAdded.getAuthor().getId());
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
        assertEquals(timeForTask,lastAdded.getTimeForTask());

        assertEquals(maxExecutionTimeMS,lastAdded.getMaxExecutionTimeMS());
//        try {
//            Thread.sleep(100000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }


    @AfterEach
    void clearCodeRunners()
    {
        codeRunnersController.reset();
    }
}