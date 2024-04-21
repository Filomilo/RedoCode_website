package com.redocode.backend.Auth;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof User)) {
            log.info("wrong instance: "+ o.getClass());
            return false;
        }
        User user=(User) o;
        return Objects.equals(id, user.id);
    }
}
