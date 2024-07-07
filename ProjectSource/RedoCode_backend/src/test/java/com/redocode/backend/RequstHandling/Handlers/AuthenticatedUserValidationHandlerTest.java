package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.WebSocketTestBase;
import com.redocode.backend.database.User;
import com.redocode.backend.database.UsersRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthenticatedUserValidationHandlerTest extends WebSocketTestBase {

    @Autowired
    UsersRepository usersRepository;

    @SneakyThrows
    @Test
    void handleUser() {
        User userAdmin = new User("uuid","nick", User.USER_TYPE.ADMIN);
        User userPremium = new User("uuid","nick", User.USER_TYPE.PREMIUM);
        User userAuthenicated = new User("uuid","nick", User.USER_TYPE.AUTHENTICATED);
        User userUnathetniacted = new User("uuid","nick", User.USER_TYPE.UNAUTHENTICATED);
        usersRepository.save(userAdmin);
        usersRepository.save(userPremium);
        usersRepository.save(userAuthenicated);
        usersRepository.save(userUnathetniacted);
        User userUnsaved = new User("uuid","nick", User.USER_TYPE.ADMIN);


        AuthenticatedUserValidationHandler authenticatedUserValidationHandler = new AuthenticatedUserValidationHandler();

//        Currenlty tunred off
//           assertNotNull(authenticatedUserValidationHandler.handle(
//                   RequestBase.builder().user(userAdmin).build()
//           ));
//        assertNotNull(authenticatedUserValidationHandler.handle(
//                   RequestBase.builder().user(userPremium).build()
//           ));
//        assertNotNull(authenticatedUserValidationHandler.handle(
//                   RequestBase.builder().user(userAuthenicated).build()
//           ));
//        assertNull(authenticatedUserValidationHandler.handle(
//                   RequestBase.builder().user(userUnathetniacted).build()
//           ));
//
//        assertNull(authenticatedUserValidationHandler.handle(
//                    RequestBase.builder().user(userUnsaved).build()
//            ));







    }
}