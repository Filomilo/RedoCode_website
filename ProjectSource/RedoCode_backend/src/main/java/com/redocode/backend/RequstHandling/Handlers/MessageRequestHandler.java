package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Auth.User;
import com.redocode.backend.ConnectionCotrollers.ConnectionTargets;
import com.redocode.backend.ConnectionCotrollers.MessageSender;
import com.redocode.backend.Messages.Handleres.ChainNodeStatusMessage;
import com.redocode.backend.SpringContextUtil;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;




public abstract class MessageRequestHandler extends BaseRequestHandler {



    MessageSender messageSender= SpringContextUtil.getApplicationContext().getBean(MessageSender.class);
    private Boolean isSilent=false;
    public void sendChainNodeStatus(User user, ChainNodeStatusMessage statusMessage)
    {
        if(!isSilent)
        {
            messageSender.sendMessage(user, ConnectionTargets.OUTNodeStatusUpdate,statusMessage);
        }
    }

    void exceptionHandling(Exception exception) {

    }
}
