package com.redocode.backend.VmAcces.CodeRunners.Variables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;

@JsonSerialize
@JsonDeserialize
@AllArgsConstructor
public class DoubleArrayOfFloats extends Variables<Float[][]> {
  public DoubleArrayOfFloats(Float[][] value) {
    super(value);
  }

  @Override
  @JsonIgnore
  public VARIABLES_TYPES getType() {
    return VARIABLES_TYPES.DOUBLE_ARRAY_OF_FLOATS;
  }
}
