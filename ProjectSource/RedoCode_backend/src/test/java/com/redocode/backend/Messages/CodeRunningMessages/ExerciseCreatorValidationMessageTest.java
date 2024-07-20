package com.redocode.backend.Messages.CodeRunningMessages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redocode.backend.WebSocketTestBase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
@Disabled("Islotating specific test for debugging")
class ExerciseCreatorValidationMessageTest  {

    @Test
    void JsonBuilder() throws JsonProcessingException {
        String json="{\n" +
                "\"amountOfAutoTests\": 1,\n" +
                "\"autoTestMaxValue\": 1,\n" +
                "\"autoTestminValue\": -1,\n" +
                "\"breakCharacterInupt\": true,\n" +
                "\"description\": \"11111111111111111111111\",\n" +
                "\"inputType\": \"SINGLE_INTEGER\",\n" +
                "\"languages\":\n" +
                "    [\n" +
                "        {\n" +
                "        \"label\": \"Cpp\",\n" +
                "        \"value\": \"cpp\"\n" +
                "        }\n" +
                "    ],\n" +
                "\"lengthRange\":\n" +
                "    {\n" +
                "    \"max\": 10,\n" +
                "    \"min\": 1\n" +
                "    },\n" +
                "\"lowerCaseInput\": true,\n" +
                "\"manualTests\":\n" +
                "    [\n" +
                "        {\n" +
                "\n" +
                "        \"expectedOutput\": \"1\",\n" +
                "        \"input\": \"1\"\n" +
                "\n" +
                "        }\n" +
                "    ],\n" +
                "\"numberInput\": true,\n" +
                "\"outputType\": \"SINGLE_INTEGER\",\n" +
                "\"ram\": 128,\n" +
                "\"solutionCodes\":\n" +
                "    {\n" +
                "    },\n" +
                "\"spaceInupt\": false,\n" +
                "\"specialCharacterInput\": true,\n" +
                "\"timeForExecutionMs\": 100,\n" +
                "\"timeForTaskMin\": 70,\n" +
                "\"title\": \"11111111111\",\n" +
                "\"upperCaseInput\": true,\n" +
                "\"xArrayRange\":\n" +
                "    {\n" +
                "    \"max\": 10,\n" +
                "    \"min\": 1\n" +
                "    },\n" +
                "\"yArrayRange\":\n" +
                "    {\n" +
                "    \"max\": 10,\n" +
                "    \"min\": 1\n" +
                "    }\n" +
                "}";
        ObjectMapper mapper = new ObjectMapper();
        ExerciseCreatorValidationMessage exerciseCreatorValidationMessage=mapper.readValue(json, ExerciseCreatorValidationMessage.class);
        assertNotNull(   exerciseCreatorValidationMessage.getManualTests().get(0).getExpectedOutput());




    }
}