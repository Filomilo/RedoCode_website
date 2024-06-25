package com.redocode.backend.Tools;

import com.redocode.backend.Messages.CodeRunningMessages.ExerciseCreatorValidationMessage;
import com.redocode.backend.Messages.UtilContainers.Range;
import com.redocode.backend.RequstHandling.Requests.ExerciseCreationRequest;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.database.User;
import org.jetbrains.annotations.NotNull;

import java.sql.Time;
import java.util.HashMap;

public class ObjectMapper {
    public static ExerciseCreationRequest toExerciseCreationRequest(ExerciseCreatorValidationMessage exerciseCreatorValidationMessage, User user) {


        return ExerciseCreationRequest.builder()
                .user(user)
                .Title(exerciseCreatorValidationMessage.getTitle())
                .Description(exerciseCreatorValidationMessage.getDescription())
                .amountOfAutoTests((int) exerciseCreatorValidationMessage.getAmountOfAutoTests())
                .lengthRange(exerciseCreatorValidationMessage.getLengthRange())
                .xArrayRange(exerciseCreatorValidationMessage.getXArrayRange())
                .yArrayRange(exerciseCreatorValidationMessage.getYArrayRange())
                .upperCaseInput(exerciseCreatorValidationMessage.isUpperCaseInput())
                .lowerCaseInput(exerciseCreatorValidationMessage.isLowerCaseInput())
                .numberInput(exerciseCreatorValidationMessage.isNumberInput())
                .specialCharacterInput(exerciseCreatorValidationMessage.isSpecialCharacterInput())
                .breakCharacterInput(exerciseCreatorValidationMessage.isBreakCharacterInupt())
                .spaceInput(exerciseCreatorValidationMessage.isSpaceInupt())
                .timeForTaskMin(exerciseCreatorValidationMessage.getTimeForTaskMin())
                .timeForExecution(exerciseCreatorValidationMessage.getTimeForExecutionMs())
                .solutionCodes(exerciseCreatorValidationMessage.getSolutionCodes())
                .testsToRun(exerciseCreatorValidationMessage.getManualTests())
                .inputType(exerciseCreatorValidationMessage.getInputType())
                .outputType(exerciseCreatorValidationMessage.getOutputType())
                .ram(exerciseCreatorValidationMessage.getRam())
                .build();
    }
}
