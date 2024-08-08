package com.redocode.backend.Messages.UtilContainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redocode.backend.Messages.CodeRunningMessages.ExerciseTestToRunMesseage;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
@Disabled("Islotating specific test for debugging")
class RangeTest {
    public static ObjectMapper mapper = new ObjectMapper();

    @Test
    @SneakyThrows
    void Serilazaiton() {
        Range range = new Range(5,10);
        byte[] bytes=mapper.writeValueAsBytes(range);
        Range read=mapper.readValue(bytes,Range.class);
        assertEquals(range,read);
    }
}