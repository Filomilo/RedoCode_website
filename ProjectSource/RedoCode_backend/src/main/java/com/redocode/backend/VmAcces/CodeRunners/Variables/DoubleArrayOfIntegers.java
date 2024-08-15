package com.redocode.backend.VmAcces.CodeRunners.Variables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonDeserialize
public class DoubleArrayOfIntegers extends Variables<Integer[][]> {
  public DoubleArrayOfIntegers(Integer[][] value) {
    super(value);
  }

  public DoubleArrayOfIntegers() {
    super();
  }

  @Override
  @JsonIgnore
  public VARIABLES_TYPES getType() {
    return VARIABLES_TYPES.DOUBLE_ARRAY_OF_INTEGERS;
  }
}
