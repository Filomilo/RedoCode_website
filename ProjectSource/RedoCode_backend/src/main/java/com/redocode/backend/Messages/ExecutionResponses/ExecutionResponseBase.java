package com.redocode.backend.Messages.ExecutionResponses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@JsonDeserialize
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public abstract class ExecutionResponseBase {
  public enum EXECUTION_RESPONSE_TYPE {
    CHAIN_SCHEME,
    STATUS_UPDATE
  }

  @JsonProperty @NotNull EXECUTION_RESPONSE_TYPE messageType;
}
