package com.redocode.backend.Messages.CodeRunningMessages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redocode.backend.Messages.UtilContainers.InputSize;
import com.redocode.backend.Messages.UtilContainers.Range;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import com.redocode.backend.database.ExerciseTests;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;

import java.sql.Time;
import java.util.HashMap;

@Value
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ExerciseCreatorValidationMessage {
    @NotNull private String title;
    @NotNull private String description;
    @NotNull private Variables.VARIABLES_TYPES inputType;
    @NotNull private Variables.VARIABLES_TYPES outputType;
    @NotNull private float amountOfAutoTests;
    @NotNull  private Range lengthRange;
    private Range xArrayRange;
    private Range yArrayRange;
    private boolean upperCaseInput;
    private boolean lowerCaseInput;
    private boolean numberInput;
    private boolean specialCharacterInput;
    private boolean breakCharacterInupt;
    private boolean spaceInupt;
    @NotNull private Long timeForTaskMin;
    @NotNull  private Long timeForExecutionMs;
    @NotNull  private HashMap<CODE_RUNNER_TYPE,String> solutionCodes;
    @NotNull  private ExerciseTests[] manualTests;
   @NotNull private int ram;
}
