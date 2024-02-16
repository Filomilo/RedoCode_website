package com.redocode.backend.Messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;



@Value
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class CodeRunnerRequestMessage {
    @JsonProperty("CodeRunnerType")
    private String CodeRunnerType;


}
