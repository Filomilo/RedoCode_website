package com.redocode.backend.VmAcces.CodeRunners.Variables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.apache.catalina.mapper.Mapper;
import org.hibernate.event.spi.SaveOrUpdateEvent;

import java.util.Arrays;
import java.util.Objects;

@Data
@SuperBuilder

@JsonSerialize
@JsonDeserialize
public abstract class Variables<T> {

    @JsonIgnore
    private int w=-1;
    @JsonIgnore
    private int h=-1;

   public enum VARIABLES_TYPES {
        SINGLE_INTEGER(0),
        SINGLE_STRING(1),
        SINGLE_FLOAT(2),
        ARRAY_OF_INTEGERS(3),
        ARRAY_STRINGS(4),
        ARRAY_OF_FLOATS(5),
        DOUBLE_ARRAY_OF_INTEGERS(6),
        DOUBLE_ARRAY_OF_STRINGS(7),
       DOUBLE_ARRAY_OF_FLOATS(8);

       private final int  id;
       VARIABLES_TYPES(int i) {
           id=i;
       }
      public int getId()
       {
           return id;
       }

       public boolean isInteger()
       {
           return id%3==0;
       }
       public boolean isString()
       {
           return id%3==1;
       }
       public boolean isFloat()
       {
           return id%3==2;
       }
       public boolean isSingle()
        {
            return id/3==0;
        }
       public  boolean isArray()
       {
           return id/3==1;
       }
       public boolean isDoubleArray()
       {
           return id/3==2;
       }

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
//
        if(getValue() instanceof Object[] && variables.getValue() instanceof Object[]) {
            Object[] arr= (Object[]) getValue();
            Object[] arro= (Object[]) variables.getValue();
            return  Arrays.deepEquals(arr,arro);
        }

        return Objects.equals(this.getValue(), variables.getValue());
    }
    @Override
    @SneakyThrows
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

//    @Override
//    public String toString() {
//        if(getValue() instanceof Object[][]) {
//            Object[][] arr= (Object[][]) getValue();
//           StringBuilder builder=new StringBuilder("{\n");
//            for (int i = 0; i < arr.length; i++) {
//                for (int j = 0; j < arr[i].length ; j++) {
//                    builder.append(arr[i][j]);
//                    if(j<arr[i].length-1)
//                    builder.append(", ");
//                }
//                builder.append("\n");
//            }
//            builder.append("}");
//            return builder.toString();
//        }
//
//        if(getValue() instanceof Object[]) {
//            Object[] arr= (Object[]) getValue();
//            StringBuilder builder=new StringBuilder("{\n");
//            for (int i = 0; i < arr.length; i++) {
//                    builder.append(arr[i]);
//                    if(i<arr.length-1)
//                        builder.append(", ");
//                }
//                builder.append("\n");
//            builder.append("}");
//            return builder.toString();
//        }
//
//
//        return getValue().toString();
//    }
}
