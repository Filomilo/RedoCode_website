package com.redocode.backend.VmAcces.CodeRunners.Variables;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redocode.backend.Tools.RedoCodeObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
//@Disabled("Islotating specific test for debugging")
class DoubleArrayOfFloatsTest {

    @Test
    void getType() {
        DoubleArrayOfFloats doubleArrayOfFloats= new DoubleArrayOfFloats();
        assertEquals(Variables.VARIABLES_TYPES.DOUBLE_ARRAY_OF_FLOATS,doubleArrayOfFloats.getType());
    }

    @Test
    void getValue() {
        Float[][] arr = {
                {1.0f,2.0f,3.0f},
                {4.0f,5.0f,6.0f},
                {7.0f,8.0f,9.0f}
        };
        DoubleArrayOfFloats var= new DoubleArrayOfFloats(arr);
        assertEquals(arr,var.getValue());
    }

    @Test
    void setValue() {
        Float[][] arr = {
                {1.0f,2.0f,3.0f},
                {4.0f,5.0f,6.0f},
                {7.0f,8.0f,9.0f}
        };
        Float[][] arr2 = {
                {10.0f,11.0f,12.0f},
                {13.0f,14.0f,15.0f},
                {18.0f,17.0f,16.0f}
        };
        DoubleArrayOfFloats var= new DoubleArrayOfFloats(arr);
        var.setValue(arr2);
        assertEquals(arr2,var.getValue());
    }


    @Test
    @SneakyThrows
    void deserialzationTest()
    {
        Float[][] arr = {
                {1.1f, 2.2f, 3.3f},
                {4.4f, 5.5f, 6.6f},
                {7.7f, 8.8f, 9.9f}
        };

        DoubleArrayOfFloats doubleArrayOfFloats=new DoubleArrayOfFloats(arr);
        com.fasterxml.jackson.databind.ObjectMapper objectMapper= new com.fasterxml.jackson.databind.ObjectMapper();
        byte[] bytes= objectMapper.writeValueAsBytes(doubleArrayOfFloats);
        DoubleArrayOfFloats res= objectMapper.readValue(bytes,DoubleArrayOfFloats.class);
        assertEquals(doubleArrayOfFloats,res);
    }
    @Test
    @SneakyThrows
    void StringifyTest()
    {
        Float[][] arr = {
                {1.1f, 2.2f, 3.3f},
                {4.4f, 5.5f, 6.6f},
                {7.7f, 8.8f, 9.9f}
        };

        DoubleArrayOfFloats doubleArrayOfFloats=new DoubleArrayOfFloats(arr);
        com.fasterxml.jackson.databind.ObjectMapper objectMapper= new com.fasterxml.jackson.databind.ObjectMapper();
       String string= objectMapper.writeValueAsString(doubleArrayOfFloats);
        DoubleArrayOfFloats res= objectMapper.readValue(string,DoubleArrayOfFloats.class);
        assertEquals(doubleArrayOfFloats,res);
    }

    @Test
    void desreialzeBadCase()
    {
        assertDoesNotThrow(()->{
            String jsonString = "[[0.9199068,0.01825869],[0.03128314,0.94099855],[0.87412083,0.5238099],[0.109776914,0.92646646],[0.9567671,0.06990063],[0.39623928,0.6877511]]";

            ObjectMapper mapper = new ObjectMapper();
            DoubleArrayOfFloats result = (DoubleArrayOfFloats) RedoCodeObjectMapper.parseVaraibles(jsonString, Variables.VARIABLES_TYPES.DOUBLE_ARRAY_OF_FLOATS);
            System.out.println(result);
        });


    }

    @Test
    @SneakyThrows
    void StringifyRegularTest()
    {
        Float[][] arr ={{1.1f,1.2f},{1.3f,1.4f}};

        DoubleArrayOfFloats doubleArrayOfFloats=new DoubleArrayOfFloats(arr);
        com.fasterxml.jackson.databind.ObjectMapper objectMapper= new com.fasterxml.jackson.databind.ObjectMapper();
        String stringFromDoubleArray= objectMapper.writeValueAsString(doubleArrayOfFloats);
        String stringFromRegularArray= objectMapper.writeValueAsString(arr);
        assertEquals(stringFromRegularArray,stringFromDoubleArray);
    }



}