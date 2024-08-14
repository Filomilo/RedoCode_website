package com.redocode.backend.ConnectionCotrollers;

import com.redocode.backend.Messages.Authentication.Authentication;
import com.redocode.backend.RedoCodeController;
import com.redocode.backend.Secuirity.JwtService;
import com.redocode.backend.database.User;
import com.redocode.backend.database.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@Slf4j
public class ApiAuthenication {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    JwtService jwtService;
    @Autowired
    RedoCodeController redoCodeController;

    @MessageMapping({ConnectionTargets.INstompAuthenticaiton})
    //todo:: consider possibluty of mapping global configuaraiton like languegs encpoint etcc to soem global config
    public void authenticationStomp(Principal principal, Authentication authentication) {
        String userId = principal.getName();
        log.info("user: " + userId + " runs authentication with otken: " + authentication.getToken());
        String mail = jwtService.extractUsername(authentication.getToken());
        User user = usersRepository.findByEmail(mail);
        if (jwtService.validateToken(authentication.getToken(), user)) {
            user.setSessionID(userId);
            redoCodeController.addConnectedUser(user);
            log.info("user: " + user + " has connected authenitcated");
        }

    }
}
