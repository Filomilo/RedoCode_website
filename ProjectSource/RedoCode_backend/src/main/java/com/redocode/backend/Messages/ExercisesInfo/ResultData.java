package com.redocode.backend.Messages.ExercisesInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Value
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(force = true)
public class ResultData {
  long executionTimeMs;
  long maxExecutionTimeMs;
  float betterThanProcent;

  @JsonProperty("SolutionRanking")
  int SolutionRanking;
}
