package com.redocode.backend.Auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void compareTo() {
        User unauthUser= new UnauthenticatedUser("22");
        User authUser= new AuthenticatedUser("2");
        User premiumUser= new PremiumUser("333");

        assertTrue(unauthUser.compareTo(authUser)>0);
        assertTrue(unauthUser.compareTo(premiumUser)>0);
        assertTrue(authUser.compareTo(unauthUser)<0);
        assertTrue(authUser.compareTo(premiumUser)>0);
        assertTrue(premiumUser.compareTo(unauthUser)<0);
        assertTrue(premiumUser.compareTo(authUser)<0);
    }



}