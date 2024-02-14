package com.redocode.backend.Auth;

public class AdminUser extends PremiumUser{
    public AdminUser(String id) {
        super(id);
    }

    @Override
   public USER_TYPE getUserType() {
        return USER_TYPE.ADMIN;
    }
}
