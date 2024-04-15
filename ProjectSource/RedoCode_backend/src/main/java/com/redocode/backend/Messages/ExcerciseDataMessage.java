package com.redocode.backend.Messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ExcerciseDataMessage {
    @JsonProperty
    List<String> availbleCodeRunners;
    @JsonProperty
    String title;
    @JsonProperty
    String desc;
    @JsonProperty
    String outputType;
    @JsonProperty
    String inputType;

    @JsonProperty
    List<ExcerciseTestMessage> tests;
    @JsonProperty
    List<ExcerciseTestMessage> automaticTests;

    @JsonProperty
    String startingFunction;




}

