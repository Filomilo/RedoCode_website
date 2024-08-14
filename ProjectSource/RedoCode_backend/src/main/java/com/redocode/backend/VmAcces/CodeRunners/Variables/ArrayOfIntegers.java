package com.redocode.backend.VmAcces.CodeRunners.Variables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.Arrays;
import java.util.Objects;

@JsonSerialize
@JsonDeserialize
public class ArrayOfIntegers extends Variables<Integer[]> {
    public ArrayOfIntegers(Integer[] value) {
        super(value);
    }

    public ArrayOfIntegers() {
        super();
    }

    @Override
    @JsonIgnore
    public VARIABLES_TYPES getType() {
        return VARIABLES_TYPES.ARRAY_OF_INTEGERS;
    }


    @Override
    public boolean equals(Object o) {
        ArrayOfIntegers variables = (ArrayOfIntegers) o;
        return Arrays.equals(this.getValue(), variables.getValue());
    }

}
