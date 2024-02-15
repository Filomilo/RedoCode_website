package com.redocode.backend;

import com.redocode.backend.Auth.UnauthenticatedUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
       String uuid=UUID.randomUUID().toString();
      log.info("new user connected: "+ uuid);
        UnauthenticatedUser user= new UnauthenticatedUser(uuid);
      RedoCodeController.getInstance().addConnectedUser(user);
        return new StompPrincipal(uuid);
    }
}
