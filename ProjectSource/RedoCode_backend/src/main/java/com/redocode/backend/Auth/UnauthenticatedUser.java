package com.redocode.backend.Auth;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UnauthenticatedUser extends User {

public UnauthenticatedUser(String id)
{
    super(id);
}

    @Override
    public USER_TYPE getUserType() {
        return USER_TYPE.UNAUTHENTICATED;
    }
}
