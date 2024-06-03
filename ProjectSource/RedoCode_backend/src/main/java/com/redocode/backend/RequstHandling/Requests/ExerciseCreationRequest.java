package com.redocode.backend.RequstHandling.Requests;

import com.redocode.backend.Messages.UtilContainers.Range;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExerciseCreationRequest extends CodeTestRequest{

    String Title;
    String Description;

    int amountOfAutoTests;
    int autoTestminValue;
    int autoTestMaxValue;
    Range lengthRange;
    Range xArrayRange;
    Range yArrayRange;
    boolean upperCaseInput;
    boolean lowerCaseInput;
    boolean numberInput;
    boolean specialCharacterInput;
    boolean breakCharacterInput;
    boolean spaceInput;


}
