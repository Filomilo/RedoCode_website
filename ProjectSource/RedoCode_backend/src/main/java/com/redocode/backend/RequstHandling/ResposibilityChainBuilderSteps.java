package com.redocode.backend.RequstHandling;

import com.redocode.backend.Messages.ExecutionResponses.ExecutionResponseBase;
import com.redocode.backend.RequstHandling.Handlers.BaseRequestHandler;
import com.redocode.backend.database.User;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ResposibilityChainBuilderSteps {
    @NotNull
    BaseRequestHandler baseRequestHandlerCurrent;
    @NotNull
    BaseRequestHandler startRequest;
    BiFunction<User, ExecutionResponseBase, Void> messagehandler;

    public ResposibilityChainBuilderSteps(BiFunction<User, ExecutionResponseBase, Void> messagehandler) {
        this.messagehandler = messagehandler;
    }

    int steps = 0;

    public ResposibilityChainBuilderSteps next(BaseRequestHandler baseRequestHandler) {
        baseRequestHandler.set_nodeLevel(steps++);
        baseRequestHandler.setMessageMethod(messagehandler);
        if (startRequest == null)
            startRequest = baseRequestHandler;
        else
            this.baseRequestHandlerCurrent.setNextRequestHandler(baseRequestHandler);
        baseRequestHandlerCurrent = baseRequestHandler;
        return this;
    }

    public ResposibilityChainBuilderSteps setContinueOnError(boolean continueOnError) {
        baseRequestHandlerCurrent.set_continueOnError(continueOnError);
        return this;
    }


    public BaseRequestHandler build() {
        return startRequest;
    }


}
