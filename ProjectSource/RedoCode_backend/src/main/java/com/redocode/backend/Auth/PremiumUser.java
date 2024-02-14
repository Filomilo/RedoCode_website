package com.redocode.backend.Auth;

public class PremiumUser extends AuthenticatedUser{
    public PremiumUser(String id) {
        super(id);
    }

    @Override
   public USER_TYPE getUserType() {
        return USER_TYPE.PREMIUM;
    }
}
