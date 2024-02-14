package com.redocode.backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomStompSessionHandler extends StompSessionHandlerAdapter {

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        log.info("Connectd: "+ session + " with headres "+ connectedHeaders);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        log.info("sesiion disconnected: "+ session);
    }
}
