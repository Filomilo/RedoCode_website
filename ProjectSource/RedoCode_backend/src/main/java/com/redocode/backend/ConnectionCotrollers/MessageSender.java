package com.redocode.backend.ConnectionCotrollers;

import com.redocode.backend.Messages.MessageNotification;
import com.redocode.backend.database.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MessageSender {

  @Autowired private final SimpMessagingTemplate template;

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
  public void sendMessageNotification(User user, MessageNotification notification) {
    sendMessageNotification(user.getSessionID(), notification);
  }
  public void sendMessageNotification(String id, MessageNotification notification) {
    sendMessage(id,"/public/topic/ServerNotifications", notification);
  }

}
