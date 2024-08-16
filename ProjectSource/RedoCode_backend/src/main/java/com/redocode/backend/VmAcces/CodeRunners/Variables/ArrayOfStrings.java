package com.redocode.backend.VmAcces.CodeRunners.Variables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonDeserialize
public class ArrayOfStrings extends Variables<String[]> {
  public ArrayOfStrings(String[] value) {
    super(value);
  }

  public ArrayOfStrings() {
    super();
  }

  @Override
  @JsonIgnore
  public VARIABLES_TYPES getType() {
    return VARIABLES_TYPES.ARRAY_STRINGS;
  }
}
