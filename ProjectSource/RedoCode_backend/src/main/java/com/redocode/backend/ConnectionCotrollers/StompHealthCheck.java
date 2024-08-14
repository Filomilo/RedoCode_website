package com.redocode.backend.ConnectionCotrollers;

import com.redocode.backend.Messages.CodeRunningMessages.RawCodeToRunMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@Slf4j
public class StompHealthCheck {

    final static String healthCheckDestin = "/public/topic/health";

    @Autowired
    MessageSender messageSender;

    @MessageMapping({ConnectionTargets.INStompHealthCheck})
    //todo:: consider possibluty of mapping global configuaraiton like languegs encpoint etcc to soem global config
    public void healthCheck(Principal principal, String healthCheckmes) {
        String userId = principal.getName();
        log.info("user: " + userId + " runs healthCheck: " + healthCheckmes);
        messageSender.sendMessage(principal.getName(), healthCheckDestin, healthCheckmes);
    }
}
