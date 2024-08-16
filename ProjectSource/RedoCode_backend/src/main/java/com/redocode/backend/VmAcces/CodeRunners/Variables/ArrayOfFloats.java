package com.redocode.backend.VmAcces.CodeRunners.Variables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonDeserialize
public class ArrayOfFloats extends Variables<Float[]> {
  public ArrayOfFloats(Float[] value) {
    super(value);
  }

  public ArrayOfFloats() {
    super();
  }

  @Override
  @JsonIgnore
  public VARIABLES_TYPES getType() {
    return VARIABLES_TYPES.ARRAY_OF_FLOATS;
  }
}
