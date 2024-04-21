package com.redocode.backend.Auth;

import javassist.CannotCompileException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@Getter
public abstract class User implements Comparable {
    public enum USER_TYPE {
        UNAUTHENTICATED,
        AUTHENTICATED,
        PREMIUM,
        ADMIN,
    }


    String id;

    User(String id) {
        this.id = id;
    }

    public abstract USER_TYPE getUserType();

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof User)) {
            log.info("wrong instance: "+ o.getClass());
            return false;
        }
        User user=(User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(@org.jetbrains.annotations.NotNull Object o) {
        User user=(User) o;

        return this.id.compareTo(user.getId());
    }
}
