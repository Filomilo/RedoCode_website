package com.redocode.backend.ConnectionCotrollers;

import com.redocode.backend.database.User;
import com.redocode.backend.VmAcces.CodeRunnerSocketController;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@AllArgsConstructor
public class MessageSender {

    @Autowired
    private final SimpMessagingTemplate template;

    public void sendMessage(String id, String dir, Object payload) {
        try {
            log.info("sending to user: " + id + " in direction: " + dir + " with data: " + payload);
            template.convertAndSendToUser(id, dir, payload);
        } catch (Exception ex) {
            log.warn("failed to send message to " + id + " exepction: " + ex.getMessage());
        }
    }

    public void sendMessage(User user, String dir, Object payload) {
        sendMessage(user.getSessionID(), dir, payload);
    }


}
