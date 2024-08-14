package com.redocode.backend.VmAcces.CodeRunners.Variables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;


@JsonSerialize
@JsonDeserialize
public class DoubleArrayOfStrings extends Variables<String[][]> {
    public DoubleArrayOfStrings(String[][] value) {
        super(value);
    }

    public DoubleArrayOfStrings() {
        super();
    }

    @Override
    @JsonIgnore
    public VARIABLES_TYPES getType() {
        return VARIABLES_TYPES.DOUBLE_ARRAY_OF_STRINGS;
    }

}
