package com.redocode.backend.Messages.ExecutionResponses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.redocode.backend.Messages.UtilContainers.ChainNodeInfo;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
public class ExecutionResponseStatusUpdate extends ExecutionResponseBase {

  @JsonProperty @NotNull int stepUpdate;
  @JsonProperty @NotNull ChainNodeInfo.CHAIN_NODE_STATUS lvlStatus;
  @JsonProperty @NotNull String message;
}
