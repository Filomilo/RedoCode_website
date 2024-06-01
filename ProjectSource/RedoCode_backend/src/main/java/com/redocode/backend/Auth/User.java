package com.redocode.backend.Auth;

import javassist.CannotCompileException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@Getter
public abstract class User implements Comparable {
    public enum USER_TYPE {
        ADMIN(0),
        PREMIUM (1),
        AUTHENTICATED(2),
        UNAUTHENTICATED(3),
        ;

        USER_TYPE(int i) {
        }


    }


    String id;

    User(String id) {
        this.id = id;
    }

    public abstract USER_TYPE getUserType();

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                "type = "+ getUserType()+
                '}';
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

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(@org.jetbrains.annotations.NotNull Object o) {

        if(!(o instanceof User))
        {
            throw  new ClassCastException("This class can only be compared with other classes derived from User class, not with "+ o.getClass());
        }
        User user= (User) o;
        int res=this.getUserType().compareTo(user.getUserType());
        return res;
    }
}
