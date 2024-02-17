package com.redocode.backend.VmAcces.CodeRunners;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
@Data
@AllArgsConstructor
public class ConsoleOutput {

    private int exitCode;
    private String output;
    private String errorOutput;
}
