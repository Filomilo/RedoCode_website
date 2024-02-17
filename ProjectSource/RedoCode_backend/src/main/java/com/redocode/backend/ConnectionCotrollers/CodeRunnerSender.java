package com.redocode.backend.ConnectionCotrollers;

import com.redocode.backend.Auth.User;
import com.redocode.backend.VmAcces.CodeRunners.Program.ProgramResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Slf4j
public class CodeRunnerSender {

    private final SimpMessagingTemplate template;

    public CodeRunnerSender(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void sendMessageToUser(String dir,Object payload, User user)
    {
        sendToUser(user.getId(),dir,payload);
    }

    public void sendToUser( String id,String dir, Object payload)
    {
        try {
            log.info("sending to user: " + id + " in direction: " + dir + " with data: " + payload);
            template.convertAndSendToUser(id, dir, payload);
        }
        catch (Exception ex)
        {
            log.warn("failed to send message to "+ id + " exepction: "+ ex.getMessage());
        }
    }




}
