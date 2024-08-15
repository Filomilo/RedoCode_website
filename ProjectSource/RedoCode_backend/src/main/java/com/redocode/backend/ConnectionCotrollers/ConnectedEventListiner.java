package com.redocode.backend.ConnectionCotrollers;


import com.redocode.backend.RedoCodeController;
import com.redocode.backend.Secuirity.JwtService;
import com.redocode.backend.StompPrincipal;
import com.redocode.backend.database.User;
import com.redocode.backend.database.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ConnectedEventListiner implements ApplicationListener<SessionConnectedEvent> {

    @Autowired
    private RedoCodeController redoCodeController;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    JwtService jwtService;

    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {

        Principal connected = (Principal) event.getMessage().getHeaders().get("simpUser");

        log.info("user with uuid connecitng stomp: " + connected.getName());
        User finalUser = new User(connected.getName());
        redoCodeController.addConnectedUser(finalUser);
    }
}
