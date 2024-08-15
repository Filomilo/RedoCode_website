package com.redocode.backend.Messages.UtilContainers;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@Value
@SuperBuilder
@Jacksonized
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Range implements Serializable { // todo: change to postgres database  type mappign
  @NotNull Float min;
  @NotNull Float max;

  public Range(Integer min, Integer max) {
    this.min = Float.valueOf(min);
    this.max = Float.valueOf(max);
  }
}
