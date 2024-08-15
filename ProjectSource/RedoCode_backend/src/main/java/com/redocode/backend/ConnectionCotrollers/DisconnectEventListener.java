package com.redocode.backend.ConnectionCotrollers;

import com.redocode.backend.RedoCodeController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DisconnectEventListener implements ApplicationListener<SessionDisconnectEvent> {

    @Autowired
    private RedoCodeController redoCodeController;
    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String userName=accessor.getUser().getName();
        log.info("User "+ userName+ " diconnected");
        redoCodeController.removeConnectedUser(userName);
    }
}