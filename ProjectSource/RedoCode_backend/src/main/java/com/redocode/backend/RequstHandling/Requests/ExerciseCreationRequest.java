package com.redocode.backend.RequstHandling.Requests;

import com.redocode.backend.Messages.UtilContainers.Range;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.sql.Time;
import java.util.HashMap;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExerciseCreationRequest extends CodeTestRequest{
    @NotNull
    String Title;
    @NotNull
    String Description;

    int amountOfAutoTests;
    Range lengthRange;
    Range xArrayRange;
    Range yArrayRange;
    boolean upperCaseInput;
    boolean lowerCaseInput;
    boolean numberInput;
    boolean specialCharacterInput;
    boolean breakCharacterInput;
    boolean spaceInput;
    @NotNull
    Long timeForTaskMin;
    @NotNull
    Long timeForExecution;
    @NotNull
    HashMap<CODE_RUNNER_TYPE,String> solutionCodes;




}
