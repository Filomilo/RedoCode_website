package com.redocode.backend.RequstHandling.Requests;

import com.redocode.backend.Messages.ExecutionResponses.ExecutionResponseBase;
import com.redocode.backend.RequstHandling.ResposibilityChainBuilderSteps;
import com.redocode.backend.database.User;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ResposibilityChainBuilder {


    BiFunction<User, ExecutionResponseBase, Void> messagehandler = (User user, ExecutionResponseBase responseBase) -> {
        return null;
    };


    public ResposibilityChainBuilder setMessagehandler(BiFunction<User, ExecutionResponseBase, Void> messagehandler) {
        this.messagehandler = messagehandler;
        return this;
    }

    public ResposibilityChainBuilderSteps setSteps() {
        return new ResposibilityChainBuilderSteps(messagehandler);
    }

}
