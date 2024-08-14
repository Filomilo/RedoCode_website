package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Messages.ExecutionResponses.ExecutionResponseBase;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.database.User;
import com.redocode.backend.ConnectionCotrollers.ConnectionTargets;
import com.redocode.backend.ConnectionCotrollers.MessageSender;
import com.redocode.backend.Messages.Handleres.ChainNodeStatusMessage;
import com.redocode.backend.SpringContextUtil;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.function.Function;


@Slf4j
public abstract class MessageRequestHandler extends BaseRequestHandler {


//todo: to remvoe classs


    void exceptionHandling(Exception exception) {
        log.info("Expection on Test: " + exception.getMessage());
    }


}
