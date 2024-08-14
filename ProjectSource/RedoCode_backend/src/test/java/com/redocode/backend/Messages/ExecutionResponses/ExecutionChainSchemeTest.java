package com.redocode.backend.Messages.ExecutionResponses;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.redocode.backend.WebSocketTestBase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
//@Disabled("Islotating specific test for debugging")
class ExecutionChainSchemeTest {
    @Test
    void ExecutionChainSchemeToJSonTest() {
        ExecutionChainScheme executionChainScheme = ExecutionChainScheme.builder()
                .messageType(ExecutionResponseBase.EXECUTION_RESPONSE_TYPE.CHAIN_SCHEME)
                .levels(new ArrayList<>())

                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        assertDoesNotThrow(() -> {
            String json = objectMapper.writeValueAsString(executionChainScheme);
            log.info("Execution chain scheme to json: {}", json);
        });
    }

}