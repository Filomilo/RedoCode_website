package com.redocode.backend.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redocode.backend.Tools.RedoCodeObjectMapper;
import com.redocode.backend.VmAcces.CodeRunners.Variables.ArrayOfIntegers;
import com.redocode.backend.VmAcces.CodeRunners.Variables.SingleInteger;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration
@Log
//@Disabled("Islotating specific test for debugging")
class ExerciseTestsRepositoryTest {

    @Autowired
    ExerciseTestsRepository exerciseTestsRepository;

    @Test
    void saveExerciseTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        SingleInteger inputVal= new SingleInteger(3);
        ArrayOfIntegers expectedOutput=new ArrayOfIntegers (new Integer[]{0,1,1});
        String inputString=  objectMapper.writeValueAsString(inputVal);
        String outputString=  objectMapper.writeValueAsString(expectedOutput);
        log.info("exerccise Test");
        ExerciseTests test= ExerciseTests.builder()
                .input(inputString)
                .expectedOutput(outputString)
        .build();
        exerciseTestsRepository.save(test);
        log.info("exerccise Test");
        log.info(inputVal+" as json: "+ inputString);
        log.info(expectedOutput+" as json: "+ outputString);

        List<ExerciseTests> exerciseTestsList= exerciseTestsRepository.findAll();
        ExerciseTests lastAdded= exerciseTestsList.get(exerciseTestsList.size()-1);

        SingleInteger inputFromTest= (SingleInteger) RedoCodeObjectMapper.parseVaraibles(lastAdded.getInput(), Variables.VARIABLES_TYPES.SINGLE_INTEGER);
        ArrayOfIntegers ouptutFromTest= (ArrayOfIntegers) RedoCodeObjectMapper.parseVaraibles(lastAdded.getExpectedOutput(), Variables.VARIABLES_TYPES.ARRAY_OF_INTEGERS);

        assertEquals(test,lastAdded);
        assertEquals(inputVal,inputFromTest);
        assertEquals(expectedOutput,ouptutFromTest);
    }
    // TODO: do this something for more variables types

}