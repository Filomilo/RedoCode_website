package com.redocode.backend.endpoints;

import com.redocode.backend.Messages.AccountInfoMessage;
import com.redocode.backend.Messages.StatisticMessage;
import com.redocode.backend.UserDataControllers.UserDataControl;
import com.redocode.backend.database.User;
import com.redocode.backend.database.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired UsersRepository usersRepository;
  @Autowired UserDataControl userDataControl;

  @GetMapping("/info")
  public ResponseEntity<AccountInfoMessage> getAccountInfo(@AuthenticationPrincipal User user) {
    User userFromDb = usersRepository.findById(user.getId()).orElse(null);
    AccountInfoMessage accountInfoMessage =
        AccountInfoMessage.builder()
            .mail(userFromDb.getEmail())
            .nickname(userFromDb.getNickname())
            .type(userFromDb.getType())
            .profilePicture(
                userFromDb.getProfilePicture() == null
                    ? ""
                    : userFromDb.getProfilePicture().getUrl())
            .build();
    return new ResponseEntity<>(accountInfoMessage, HttpStatus.OK);
  }

  @GetMapping("/stats")
  public ResponseEntity<StatisticMessage> getUserStats(@AuthenticationPrincipal User user) {
    User userFromDb = usersRepository.findById(user.getId()).orElse(null);
    StatisticMessage statisticMessage = userDataControl.getUserStats(userFromDb.getId());
    return new ResponseEntity<>(statisticMessage, HttpStatus.OK);
  }
}
