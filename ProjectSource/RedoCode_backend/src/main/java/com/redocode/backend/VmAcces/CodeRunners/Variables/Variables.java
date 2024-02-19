package com.redocode.backend.VmAcces.CodeRunners.Variables;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public abstract class Variables<T> {

    private int w=-1;
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

}
