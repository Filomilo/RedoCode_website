package com.redocode.backend.Messages.CodeRunningMessages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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

import java.util.List;
import java.util.Map;

@Value
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ExerciseCreatorValidationMessage {
    @NotNull @JsonProperty("title") private String title;
    @NotNull @JsonProperty("description") private String description;
    @NotNull @JsonProperty("inputType") private Variables.VARIABLES_TYPES inputType;
    @NotNull @JsonProperty("outputType") private Variables.VARIABLES_TYPES outputType;
    @NotNull @JsonProperty("amountOfAutoTests") private float amountOfAutoTests;
    @NotNull @JsonProperty("lengthRange") private Range lengthRange;
    @JsonProperty("xArrayRange") private Range xArrayRange;
    @JsonProperty("yArrayRange") private Range yArrayRange;
    @JsonProperty("upperCaseInput") private boolean upperCaseInput;
    @JsonProperty("lowerCaseInput") private boolean lowerCaseInput;
    @JsonProperty("numberInput") private boolean numberInput;
    @JsonProperty("specialCharacterInput") private boolean specialCharacterInput;
    @JsonProperty("breakCharacterInupt") private boolean breakCharacterInupt;
    @JsonProperty("spaceInupt") private boolean spaceInupt;
    @NotNull @JsonProperty("timeForTaskMin") private Long timeForTaskMin;
    @NotNull @JsonProperty("timeForExecutionMs") private Long timeForExecutionMs;
    @NotNull @JsonProperty("solutionCodes") private Map<CODE_RUNNER_TYPE,String> solutionCodes;
    @NotNull @JsonProperty("manualTests") private List<ExerciseTests> manualTests;
    @NotNull @JsonProperty("ram") private int ram;
}
