package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.database.User;
import com.redocode.backend.database.UsersRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

// @Disabled("Isotating specific test for debugging")
@SpringBootTest
class AuthenticatedUserValidationHandlerTest {

  @Autowired UsersRepository usersRepository;

  @SneakyThrows
  @Test
  void handleUser() {
    User userAdmin =
        User.builder()
            .sessionID("uuid")
            .email("adminADMIN@admin.com")
            .nickname("nick")
            .type(User.USER_TYPE.ADMIN)
            .password("password")
            .ProfilePicture(null)
            .build();

    User userPremium =
        User.builder()
            .sessionID("uuid")
            .email("adminPREMIUM@admin.com")
            .nickname("nick")
            .type(User.USER_TYPE.PREMIUM)
            .password("password")
            .ProfilePicture(null)
            .build();
    User userAuthenicated =
        User.builder()
            .sessionID("uuid")
            .email("adminAUTHENTICATED@admin.com")
            .nickname("nick")
            .password("password")
            .ProfilePicture(null)
            .type(User.USER_TYPE.AUTHENTICATED)
            .build();
    User userUnathetniacted =
        User.builder()
            .sessionID("uuid")
            .email("adminUNAUTHENTICATED@admin.com")
            .nickname("nick")
            .password("password")
            .type(User.USER_TYPE.UNAUTHENTICATED)
            .ProfilePicture(null)
            .build();
    usersRepository.save(userAdmin);
    usersRepository.save(userPremium);
    usersRepository.save(userAuthenicated);
    usersRepository.save(userUnathetniacted);
    User userUnsaved = new User("uuid", "nick", User.USER_TYPE.ADMIN);

    AuthenticatedUserValidationHandler authenticatedUserValidationHandler =
        new AuthenticatedUserValidationHandler();

    assertNotNull(
        authenticatedUserValidationHandler.handle(RequestBase.builder().user(userAdmin).build()));
    assertNotNull(
        authenticatedUserValidationHandler.handle(RequestBase.builder().user(userPremium).build()));
    assertNotNull(
        authenticatedUserValidationHandler.handle(
            RequestBase.builder().user(userAuthenicated).build()));
    assertNull(
        authenticatedUserValidationHandler.handle(
            RequestBase.builder().user(userUnathetniacted).build()));

    assertNull(
        authenticatedUserValidationHandler.handle(RequestBase.builder().user(userUnsaved).build()));
  }
}
