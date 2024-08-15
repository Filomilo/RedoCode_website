package com.redocode.backend.RequstHandling.Requests;

import com.redocode.backend.Messages.UtilContainers.Range;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import com.redocode.backend.database.ExerciseTests;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Setter
public class CodeTestRequest extends RawCodeRunRequest {

  List<ExerciseTests> testsToRun;
  List<ExerciseTests> AutotestsToRun = new ArrayList<>();
  Variables.VARIABLES_TYPES inputType;
  Variables.VARIABLES_TYPES outputType;

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
  @NotNull Long timeForTaskMin;
  @NotNull Map<CODE_RUNNER_TYPE, String> solutionCodes;
}
