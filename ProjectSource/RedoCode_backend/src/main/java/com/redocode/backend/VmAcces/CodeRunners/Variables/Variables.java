package com.redocode.backend.VmAcces.CodeRunners.Variables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.Objects;

@Data
@SuperBuilder
public abstract class Variables<T> {

    @JsonIgnore
    private int w=-1;
    @JsonIgnore
    private int h=-1;

   public enum VARIABLES_TYPES {
        SINGLE_INTEGER,
        SINGLE_STRING,
        SINGLE_FLOAT,
        ARRAY_OF_INTEGERS,
        ARRAY_STRINGS,
        ARRAY_OF_FLOATS,
        DOUBLE_ARRAY_OF_INTEGERS,
        DOUBLE_ARRAY_OF_FLOATS,
        DOUBLE_ARRAY_OF_STRINGS
    }


    @Getter
    @JsonProperty
    T value;


    public void setValue(T value) {
        this.value=value;
        if(value instanceof Object[])
        {
            this.w=((Object[])value).length;
            if(value instanceof Object[][])
            {
                this.w=((Object[][])value)[0].length;
                this.h=((Object[][])value).length;
            }
        }
    }

    public Variables(T value) {
        setValue(value);
    }
    public Variables()
    {

    }
    public abstract VARIABLES_TYPES getType();



    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object o) {
        Variables<T> variables = (Variables<T>) o;
        return Objects.equals(this.getValue(), variables.getValue());
    }
}
