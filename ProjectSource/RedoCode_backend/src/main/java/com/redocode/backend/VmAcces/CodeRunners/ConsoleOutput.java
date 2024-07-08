package com.redocode.backend.VmAcces.CodeRunners;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
@Data
@Value
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ConsoleOutput {
    @NotNull
    private int exitCode;
    @NotNull
    private String output;
    @NotNull
    private String errorOutput;
}
