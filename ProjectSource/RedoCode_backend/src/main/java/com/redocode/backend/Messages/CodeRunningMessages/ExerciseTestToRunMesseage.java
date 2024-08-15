package com.redocode.backend.Messages.CodeRunningMessages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.redocode.backend.Messages.UtilContainers.Range;
import com.redocode.backend.database.ExerciseTests;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
import java.util.List;

@Value
@SuperBuilder
@Jacksonized
@NoArgsConstructor(force = true)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExerciseTestToRunMesseage implements Serializable {
  private String code;
  private String inputType;
  private String outputType;
  private int amountOfAutoTests;
  private float autoTestminValue;
  private float autoTestMaxValue;
  private Range lengthRange;

  @JsonProperty("sArrayRange")
  private Range xArrayRange;

  @JsonProperty("yArrayRange")
  private Range yArrayRange;

  private boolean upperCaseInput;
  private boolean lowerCaseInput;
  private boolean numberInput;
  private boolean specialCharacterInput;
  private boolean breakCharacterInupt;
  private boolean spaceInupt;
  private Long executionTime;
  @NotNull List<ExerciseTests> manualTests;
}
