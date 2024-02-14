package com.redocode.backend.Auth;

public class AuthenticatedUser extends UnauthenticatedUser{

    public AuthenticatedUser(String id) {
        super(id);
    }

    @Override
   public USER_TYPE getUserType() {
        return USER_TYPE.AUTHENTICATED;
    }
}
