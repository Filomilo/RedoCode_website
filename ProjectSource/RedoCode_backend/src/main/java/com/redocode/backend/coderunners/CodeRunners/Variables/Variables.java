package com.redocode.backend.coderunners.CodeRunners.Variables;

public abstract class Variables<T> {
    enum VARIABLES_TYPES {
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


    T value;

    public Variables(T value) {
        this.value = value;
    }
    public Variables()
    {

    }


    abstract VARIABLES_TYPES getType();
    public void setValue(T value)
     {
         this.value=value;
     }
    public T getValue()
    {
        return value;
    }

}
