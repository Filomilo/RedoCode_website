package com.redocode.backend.Messages.UtilContainers;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Builder
@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@JsonDeserialize
@AllArgsConstructor
public class ChainNodeInfo {
    public enum CHAIN_NODE_STATUS {
        PENDING,
        RUNNING,
        SUCCESS,
        FAILED
    }

    String nodeName;
    CHAIN_NODE_STATUS status;
    String processingMessage;

}
