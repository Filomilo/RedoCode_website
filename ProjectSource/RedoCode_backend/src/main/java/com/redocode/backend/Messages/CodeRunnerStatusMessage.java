package com.redocode.backend.Messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.redocode.backend.VmAcces.VmStatus;
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
public class CodeRunnerStatusMessage {

    @JsonProperty
    VmStatus vmStatus;

}
