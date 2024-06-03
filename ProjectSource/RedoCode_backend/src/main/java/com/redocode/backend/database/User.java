package com.redocode.backend.database;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;


import java.util.Objects;

@Entity
@Table(name ="users")
@Data
@Slf4j
@NoArgsConstructor
public class User implements Comparable {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Id
    private Long id;

    @Transient
    private String sessionID;


    public User(String session, String nick, USER_TYPE userType) {
        this.sessionID = session;
        this.userName=nick;
        this.type=userType;
    }

    public User(String uuid) {
        this(uuid,null,USER_TYPE.UNAUTHENTICATED);
    }

    public enum USER_TYPE {
        ADMIN(0),
        PREMIUM(1),
        AUTHENTICATED(2),
        UNAUTHENTICATED(3),
        ;

        USER_TYPE(int i) {
        }

    }


    @Column(
            name = "user_name"
    )
    @NotNull
    private String userName;

    @Column(
            name = "user_type"
    )
//    @Min(0)
//    @Max(2)
    @NotNull
    private USER_TYPE type;




        @Override
    public boolean equals(Object o) {
            log.info("copmaring "+this.toString()+"with "+ o.toString());
        if(!(o instanceof User)) {
            log.info("wrong instance: "+ o.getClass());
            return false;
        }
        User user=(User) o;
        return Objects.equals(this.sessionID, user.sessionID);
    }



    @Override
    public int hashCode() {
        return Objects.hashCode(this.sessionID);
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

    public USER_TYPE getUserType() {
            return type;
    }

}
