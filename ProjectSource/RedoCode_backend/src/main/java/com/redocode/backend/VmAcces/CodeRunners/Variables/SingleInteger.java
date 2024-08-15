package com.redocode.backend.VmAcces.CodeRunners.Variables;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

@JsonSerialize
@JsonDeserialize
@AllArgsConstructor
public class SingleInteger extends Variables<Integer> {
    public SingleInteger(Integer value) {
        super(value);
    }
    @Override
    @JsonIgnore
    public VARIABLES_TYPES getType() {
       return VARIABLES_TYPES.SINGLE_INTEGER;
    }

}
