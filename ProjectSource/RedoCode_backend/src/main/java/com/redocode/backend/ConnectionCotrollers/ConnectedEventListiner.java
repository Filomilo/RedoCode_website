package com.redocode.backend.ConnectionCotrollers;


import com.redocode.backend.Auth.UnauthenticatedUser;
import com.redocode.backend.RedoCodeController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.security.Principal;
import java.util.Arrays;

@Slf4j
@Component
public class ConnectedEventListiner implements ApplicationListener<SessionConnectedEvent> {

    @Autowired
    private RedoCodeController redoCodeController;


    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
        log.info("event: "+event.getMessage());
        log.info("Connect eventL: "+ Arrays.toString(event.getMessage().getHeaders().keySet().toArray()));

        log.info("user: "+event.getMessage().getHeaders().get("simpUser"));
        Principal connected= (Principal) event.getMessage().getHeaders().get("simpUser");
        redoCodeController.addConnectedUser(new UnauthenticatedUser(connected.getName()));
    }
}
