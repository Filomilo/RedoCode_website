package com.redocode.backend.Messages.CodeRunningMessages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redocode.backend.Messages.UtilContainers.InputSize;
import com.redocode.backend.Messages.UtilContainers.Range;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Value
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ExerciseTestToRunMesseage {
private String code;
    private String inputType;
    private String inputSize;
    private String outputType;
    private String outputSize;
    private float amountOfAutoTests;
    private float autoTestminValue;
    private float autoTestMaxValue;
    private Range lengthRange;
    private Range xArrayRange;
    private Range yArrayRange;
    private boolean upperCaseInput;
    private boolean lowerCaseInput;
    private boolean numberInput;
    private boolean specialCharacterInput;
    private boolean breakCharacterInupt;
    private boolean spaceInupt;
}