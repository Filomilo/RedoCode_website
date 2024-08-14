package com.redocode.backend.Messages.CodeRunningMessages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redocode.backend.VmAcces.CodeRunners.Program.ProgramResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(force = true)
@AllArgsConstructor

public class ProgramResultsMessage {
    List<ProgramResult> results;
}
