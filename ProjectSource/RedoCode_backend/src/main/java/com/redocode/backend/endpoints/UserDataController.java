package com.redocode.backend.endpoints;

import com.redocode.backend.Messages.AccountInfoMessage;
import com.redocode.backend.database.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/secure/user")
public class UserDataController {

  @GetMapping("/info")
  public ResponseEntity<AccountInfoMessage> getAccountInfo(@AuthenticationPrincipal User user) {
    AccountInfoMessage accountInfoMessage =
        AccountInfoMessage.builder()
            .mail(user.getEmail())
            .nickname(user.getNickname())
            .type(user.getType())
            .profilePicture(
                user.getProfilePicture() == null ? "" : user.getProfilePicture().getUrl())
            .build();
    return new ResponseEntity<>(accountInfoMessage, HttpStatus.OK);
  }
}
