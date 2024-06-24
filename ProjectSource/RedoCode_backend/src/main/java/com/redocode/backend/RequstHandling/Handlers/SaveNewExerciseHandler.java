package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.RequstHandling.Requests.ExerciseCreationRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.SpringContextUtil;
import com.redocode.backend.database.Excersize;
import com.redocode.backend.database.ExerciseRepository;
import com.redocode.backend.database.ExerciseTests;
import com.redocode.backend.database.ExerciseTestsRepository;
import java.util.Arrays;

public class SaveNewExerciseHandler extends MessageRequestHandler{

    static final ExerciseRepository  exerciseRepository= SpringContextUtil.getApplicationContext().getBean(ExerciseRepository.class);
    static final ExerciseTestsRepository  exerciseTestsRepository= SpringContextUtil.getApplicationContext().getBean(ExerciseTestsRepository.class);
    @Override
    boolean handle(RequestBase request) throws RequestHadndlingException {
        ExerciseCreationRequest exerciseCreationRequest= (ExerciseCreationRequest) request;

        Excersize excersize= Excersize.builder()
                .excersizeName(exerciseCreationRequest.getTitle())
                .ram_mb(exerciseCreationRequest.getRam())
                .outputType(exerciseCreationRequest.getOutputType())
                .inputType(exerciseCreationRequest.getInputType())
                .amountOfAutoTests(exerciseCreationRequest.getAmountOfAutoTests())
                .author( exerciseCreationRequest.getUser())
                .description(exerciseCreationRequest.getDescription())
                .exerciseTests(Arrays.stream(exerciseCreationRequest.getTestsToRun()).toList())
                .breakCharacterInput(exerciseCreationRequest.isBreakCharacterInput())
                .lowerCaseInput(exerciseCreationRequest.isBreakCharacterInput())
                .numberInput(exerciseCreationRequest.isNumberInput())
                .spaceInput(exerciseCreationRequest.isSpaceInput())
                .specialCharacterInput(exerciseCreationRequest.isSpecialCharacterInput())
                .upperCaseInput(exerciseCreationRequest.isUpperCaseInput())
                .valueLengthRangeMin(exerciseCreationRequest.getLengthRange().getMin())
                .valueLengthRangeMax(exerciseCreationRequest.getLengthRange().getMax())
                .arrayXLengthRangeMin(exerciseCreationRequest.getXArrayRange().getMin().intValue())
                .arrayXLengthRangeMax(exerciseCreationRequest.getXArrayRange().getMax().intValue())
                .arrayYLengthRangeMin(exerciseCreationRequest.getYArrayRange().getMin().intValue())
                .arrayYLengthRangeMax(exerciseCreationRequest.getYArrayRange().getMax().intValue())
                .timeForTask(exerciseCreationRequest.getTimeForTask())
                .maxExecutionTimeMS(exerciseCreationRequest.getTimeForExecution())
                .build();
        for (ExerciseTests test: exerciseCreationRequest.getTestsToRun()
        ) {
            test.setExcersize(excersize);
        }
       exerciseRepository.save (excersize);
       return true;
    }
}
