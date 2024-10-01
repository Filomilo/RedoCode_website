package com.redocode.backend.endpoints;

import com.redocode.backend.Messages.*;
import com.redocode.backend.Tools.RedoCodeObjectMapper;
import com.redocode.backend.UserDataControllers.UserDataControl;
import com.redocode.backend.database.User;
import com.redocode.backend.database.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

  @PostMapping("/profilePicture")
  public ResponseEntity<String> getUserStats(
      @AuthenticationPrincipal User user, @RequestBody AccountPicRequest request) {
    log.info("profilePicture requst: " + request);
    try {
      userDataControl.changeAccountImage(
          user.getId(),
          RedoCodeObjectMapper.jsonMessageToBase64(request.getImage()),
          RedoCodeObjectMapper.jsonMessageToExtension(request.getImage()));
    } catch (Exception e) {
      log.error(e.getMessage());
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/changePassword")
  public ResponseEntity<String> changePassword(
      @AuthenticationPrincipal User user, @RequestBody PasswordChangeMessage request) {
    log.info("changePassword requst: " + request);
    try {
      userDataControl.changePassword(user.getId(), request.getPassword(), request.getNewPassword());
    } catch (Exception e) {
      log.error(e.getMessage());
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/remove")
  public ResponseEntity<String> removeAccount(
      @AuthenticationPrincipal User user, @RequestBody RemoveAccountMessage request) {
    log.info("removeAccount requst: " + request);
    try {
      userDataControl.removeAccount(user.getId(), request.getPassword());
    } catch (Exception e) {
      log.error(e.getMessage());
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/details")
  public ResponseEntity<?> getUserDetails(@AuthenticationPrincipal User user) {
    log.info("getUserDetails requst: " + user.getNickname());
    try {
      return new ResponseEntity<UserDetailsMessage>(
          userDataControl.getUserDetails(user.getId()), HttpStatus.OK);
    } catch (Exception e) {
      log.error(e.getMessage());
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/description")
  public ResponseEntity<String> setDescription(
      @AuthenticationPrincipal User user, @RequestBody PostDescriptionMessage request) {
    log.info("setDescription requst: " + request);
    try {
      userDataControl.setDescription(user.getId(), request.getDescription());
    } catch (Exception e) {
      log.error(e.getMessage());
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
