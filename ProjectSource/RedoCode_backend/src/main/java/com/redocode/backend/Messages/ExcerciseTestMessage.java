package com.redocode.backend.Messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Value
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ExcerciseTestMessage {
    @JsonProperty
    Object input;
    @JsonProperty
    Object output;
    @JsonProperty
    Object expectedOutput;
    @JsonProperty
    String errorOutput;
    @JsonProperty
    String consoleOutput;
    @JsonProperty
    Boolean isSolved;
}
