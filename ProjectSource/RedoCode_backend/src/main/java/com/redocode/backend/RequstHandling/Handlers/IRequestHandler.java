package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.RequstHandling.Requests.RequestBase;

public interface IRequestHandler {

    public boolean next(RequestBase request);
}
