package com.redocode.backend.ConnectionCotrollers;

import com.redocode.backend.Messages.CodeRunningMessages.ExerciseCreatorValidationMessage;
import com.redocode.backend.Messages.UtilContainers.Range;
import com.redocode.backend.StompPrincipal;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import com.redocode.backend.database.Excersize;
import com.redocode.backend.database.ExerciseRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.security.Principal;
import java.sql.Time;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ContextConfiguration
class CodeRunHandlerTest {
@Autowired
    CodeRunHandler codeRunHandler;
@Autowired
    ExerciseRepository exerciseRepository;
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
        String userId="1";
;        Variables.VARIABLES_TYPES inputType= Variables.VARIABLES_TYPES.DOUBLE_ARRAY_OF_STRINGS;
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




        ExerciseCreatorValidationMessage creatorValidationMessage=
                ExerciseCreatorValidationMessage.builder()



                        .build();

        StompPrincipal principal=new StompPrincipal(userId);
        codeRunHandler.runExerciseCreatorValidationCode(principal,creatorValidationMessage);



        Excersize lastAdded=exerciseRepository.findAll().get(exerciseRepository.findAll().size()-1);
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
        assertEquals(timeForTask,lastAdded.getTimeForTask());

        assertEquals(maxExecutionTimeMS,lastAdded.getMaxExecutionTimeMS());

    }
}