package com.redocode.backend.Messages.CodeRunningMessages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redocode.backend.Messages.UtilContainers.Range;
import com.redocode.backend.WebSocketTestBase;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//@Disabled("Islotating specific test for debugging")
class ExerciseTestToRunMesseageTest {

    public static ObjectMapper mapper = new ObjectMapper();

    @Test
    @SneakyThrows
    void Serilazaiton() {
        ExerciseTestToRunMesseage exercise = ExerciseTestToRunMesseage.builder()
                .xArrayRange(new Range(-5, 5))
                .yArrayRange(new Range(-5, 5))
                .build();
        byte[] bytes = mapper.writeValueAsBytes(exercise);
        ExerciseTestToRunMesseage read = mapper.readValue(bytes, ExerciseTestToRunMesseage.class);
        assertEquals(exercise, read);
    }
}