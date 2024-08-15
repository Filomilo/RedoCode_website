package com.redocode.backend.RequstHandling;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.Messages.UtilContainers.Range;
import com.redocode.backend.RequstHandling.Requests.ExerciseCreationRequest;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import com.redocode.backend.VmAcces.CodeRunnersController;
import com.redocode.backend.database.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.sql.Time;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
//@Disabled("Not wokrking in gihtub pipleine eveneroment")
//@Disabled("Islotating specific test for debugging")
class ResponsibilityChainRepositoryExerciseCreationTest {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    CodeRunnersController codeRunnersController;
    @Autowired
    ExerciseRepository exerciseRepository;

    User userCorrect;

    @BeforeEach
    void setupCorrectData()
    {
        userCorrect=usersRepository.findByEmail("email@email.com");
        if(userCorrect==null) {
            userCorrect = User.builder()
                    .email("email"+ UUID.randomUUID()+"@email.com")
                    .type(User.USER_TYPE.PREMIUM)
                    .password("aaaa")
                    .sessionID("uuid"+ UUID.randomUUID())
                    .nickname("nick")
                    .build();


            usersRepository.save(userCorrect);
        }

        exerciseCreationRequestCorrect  = ExerciseCreationRequest.builder()
                .user(userCorrect)
                .outputType(ouptutTypeCorrect)
                .inputType(inputTypeCorrect)
                .ram(ramCorrect)
                .Title(titleCorrect)
                .Description(decritpionCorrect)
                .solutionCodes(solutionCodesCorrect)
                .amountOfAutoTests(amountOfAutoTestsCorrect)
                .breakCharacterInput(breakCharacterInputCorrect)
                .lengthRange(lengthRangeCorrect)
                .lowerCaseInput(lowerCaseInputCorrect)
                .numberInput(numberInputCorrect)
                .spaceInput(spaceInputCorrect)
                .specialCharacterInput(specialCharacterInputCorrect)
                .upperCaseInput(upperCaseInputCorrect)
                .xArrayRange(xArrayRangeCorrect)
                .yArrayRange(yArrayRangeCorrect)
                .testsToRun(Arrays.stream(testsCorrect).toList())
                .timeForTaskMin(timeForTaskCorrect)
                .timeForExecution(maxExecutionTimeMSCorrect)
                .build();

    }




    Variables.VARIABLES_TYPES inputTypeCorrect= Variables.VARIABLES_TYPES.DOUBLE_ARRAY_OF_STRINGS;
    Variables.VARIABLES_TYPES ouptutTypeCorrect= Variables.VARIABLES_TYPES.DOUBLE_ARRAY_OF_STRINGS;
    int ramCorrect=1024;
    String titleCorrect="Exercise";
    String decritpionCorrect="Descritpion";

    int amountOfAutoTestsCorrect=8;
    boolean breakCharacterInputCorrect=true;
    Range lengthRangeCorrect=new Range(0F,20F);
    boolean lowerCaseInputCorrect=true;
    boolean numberInputCorrect=true;
    boolean spaceInputCorrect=true;
    boolean specialCharacterInputCorrect=true;
    boolean upperCaseInputCorrect=true;
    Range xArrayRangeCorrect= new Range(1F,20F);
    Range yArrayRangeCorrect=new Range(1F,20F);
    Long timeForTaskCorrect= 60L;
    Long maxExecutionTimeMSCorrect=1000L;

    HashMap<CODE_RUNNER_TYPE,String> solutionCodesCorrect=new HashMap<>()
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
    ExerciseTests[] testsCorrect= new ExerciseTests[]{
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

    ExerciseCreationRequest exerciseCreationRequestCorrect;





    @Test
    void testCoreectExerciseCreation()
    {


assertDoesNotThrow(
        ()->{
            assertTrue(ResponsibilityChainRepository.createNewExercise.next(exerciseCreationRequestCorrect));
        }
);


        Excersize lastAdded=exerciseRepository.findAll().get(exerciseRepository.findAll().size()-1);
        assertEquals(titleCorrect,lastAdded.getExcersizeName());
        assertEquals(inputTypeCorrect,lastAdded.getInputType());
        assertEquals(ouptutTypeCorrect,lastAdded.getOutputType());
        assertEquals(ramCorrect,lastAdded.getRam_mb());
        assertEquals(decritpionCorrect,lastAdded.getDescription());
        assertEquals(userCorrect.getId(),lastAdded.getAuthor().getId());
        assertEquals(Arrays.stream(testsCorrect).toList(),lastAdded.getExerciseTests().stream().toList());


        assertEquals(breakCharacterInputCorrect,lastAdded.getBreakCharacterInput());
        assertEquals(lowerCaseInputCorrect,lastAdded.getLowerCaseInput());
        assertEquals(breakCharacterInputCorrect,lastAdded.getLowerCaseInput());
        assertEquals(numberInputCorrect,lastAdded.getNumberInput());
        assertEquals(spaceInputCorrect,lastAdded.getSpaceInput());
        assertEquals(specialCharacterInputCorrect,lastAdded.getSpecialCharacterInput());
        assertEquals(upperCaseInputCorrect,lastAdded.getUpperCaseInput());

        assertEquals(lengthRangeCorrect,lastAdded.getLengthRange());
        assertEquals(xArrayRangeCorrect,lastAdded.getXArrayRange());
        assertEquals(yArrayRangeCorrect,lastAdded.getYArrayRange());

        assertEquals(amountOfAutoTestsCorrect,lastAdded.getAmountOfAutoTests());
        assertEquals(timeForTaskCorrect,lastAdded.getTimeForTaskMin());

        assertEquals(maxExecutionTimeMSCorrect,lastAdded.getMaxExecutionTimeMS());

    }




    @ParameterizedTest
    @ValueSource(strings = {"", "         ","1","22","333",""})
    void testIncorrectTitleExerciseCreation(String incorrectTitle)
    {
        int exerciseBefore=exerciseRepository.findAll() .size();
        ExerciseCreationRequest exerciseCreationRequest= this.exerciseCreationRequestCorrect;
        exerciseCreationRequest.setTitle(incorrectTitle);
        assertFalse(  ResponsibilityChainRepository.createNewExercise.next(exerciseCreationRequest));
        int exerciseAfter=exerciseRepository.findAll() .size();
        assertEquals(exerciseBefore,exerciseAfter);
    }
    @ParameterizedTest
    @ValueSource(strings = {"", "         ","1","22","333",""})
    void testIncorrectDescriptionExerciseCreation(String incorrectdesc)
    {
        int exerciseBefore=exerciseRepository.findAll() .size();
        ExerciseCreationRequest exerciseCreationRequest= this.exerciseCreationRequestCorrect;
        exerciseCreationRequest.setDescription(incorrectdesc);
        assertFalse(  ResponsibilityChainRepository.createNewExercise.next(exerciseCreationRequest));
        int exerciseAfter=exerciseRepository.findAll() .size();
        assertEquals(exerciseBefore,exerciseAfter);
    }

    @Test
    void testIncorrectSolutionJsExerciseCreation()
    {
        int exerciseBefore=exerciseRepository.findAll() .size();
        ExerciseCreationRequest exerciseCreationRequest= this.exerciseCreationRequestCorrect;
        HashMap<CODE_RUNNER_TYPE,String> solutions=solutionCodesCorrect;
        solutions.put(CODE_RUNNER_TYPE.JS_RUNNER,"function solution(array){return 0;}");
        exerciseCreationRequest.setSolutionCodes(solutions);
        assertFalse(  ResponsibilityChainRepository.createNewExercise.next(exerciseCreationRequest));
        int exerciseAfter=exerciseRepository.findAll() .size();
        assertEquals(exerciseBefore,exerciseAfter);
    }

    @Test
    void testIncorrectSolutionCppExerciseCreation()
    {
        int exerciseBefore=exerciseRepository.findAll() .size();
        ExerciseCreationRequest exerciseCreationRequest= this.exerciseCreationRequestCorrect;
        HashMap<CODE_RUNNER_TYPE,String> solutions=solutionCodesCorrect;
        solutions.put(CODE_RUNNER_TYPE.CPP_RUNNER,"#include <iostream>\n" +
                "#include <vector>\n" +
                "#include <string>\n" +
                "\n" +
                "std::vector<std::vector<std::string>> solution(std::vector<std::vector<std::string>> in)\n" +
                "{\n" +
                "    return null;\n" +
                "}");
        exerciseCreationRequest.setSolutionCodes(solutions);
        assertFalse(  ResponsibilityChainRepository.createNewExercise.next(exerciseCreationRequest));
        int exerciseAfter=exerciseRepository.findAll() .size();
        assertEquals(exerciseBefore,exerciseAfter);
    }







    @AfterEach
    void clearCodeRunners()
    {
        codeRunnersController.reset();
    }
}