package com.redocode.backend.Auth;

import com.redocode.backend.database.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.lang.ref.PhantomReference;

import static org.junit.jupiter.api.Assertions.*;
@Disabled("Islotating specific test for debugging")
class UserTest {

    @Test
    void compareTo() {
        User unauthUser= new User("22");
        User authUser= new User("2","nick", User.USER_TYPE.AUTHENTICATED);
        User premiumUser= new User("333","test", User.USER_TYPE.PREMIUM);

        assertTrue(unauthUser.compareTo(authUser)>0);
        assertTrue(unauthUser.compareTo(premiumUser)>0);
        assertTrue(authUser.compareTo(unauthUser)<0);
        assertTrue(authUser.compareTo(premiumUser)>0);
        assertTrue(premiumUser.compareTo(unauthUser)<0);
        assertTrue(premiumUser.compareTo(authUser)<0);
    }
}