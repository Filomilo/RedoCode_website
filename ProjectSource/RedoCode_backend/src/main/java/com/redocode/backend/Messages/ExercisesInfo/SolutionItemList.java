package com.redocode.backend.Messages.ExercisesInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;
@Value
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(force = true)
public class SolutionItemList {
    @NotEmpty
            @NotBlank
            @NotNull
    @JsonProperty("username")
    String username;
    @NotNull
    @JsonProperty("date")
    Date date;
    @JsonProperty("executionTimeMs")
    long executionTimeMs;
    @NotEmpty
    @NotBlank
    @NotNull
    @JsonProperty("profilePic")
    String profilePic;
    @JsonProperty("solutionId")
    long solutionId;
    @NotEmpty
    @NotBlank
    @NotNull
    @JsonProperty("codeRunner")
    CODE_RUNNER_TYPE codeRunner;
}
