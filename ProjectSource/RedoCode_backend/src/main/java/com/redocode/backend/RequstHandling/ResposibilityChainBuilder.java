package com.redocode.backend.RequstHandling;

import com.redocode.backend.RequstHandling.Handlers.BaseRequestHandler;
import org.jetbrains.annotations.NotNull;

public class ResposibilityChainBuilder {
    @NotNull
    BaseRequestHandler baseRequestHandlerCurrent;
    @NotNull
    BaseRequestHandler startRequest;
    ResposibilityChainBuilder(BaseRequestHandler baseRequestHandler)
    {
        baseRequestHandlerCurrent=baseRequestHandler;
        startRequest=baseRequestHandler;
    }

    public ResposibilityChainBuilder next(BaseRequestHandler baseRequestHandler)
    {
      this.baseRequestHandlerCurrent.setNextRequestHandler(baseRequestHandler);
      baseRequestHandlerCurrent=baseRequestHandler;
      return this;
    }

    public BaseRequestHandler build()
    {
        return startRequest;
    }




}
