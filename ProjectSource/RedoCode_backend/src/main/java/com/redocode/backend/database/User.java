package com.redocode.backend.database;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Comparable, UserDetails {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Setter
  @Id
  private Long id;

  @Transient private String sessionID;

  @Column(name = "email", unique = true)
  @Email
  @NotEmpty
  @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
  private String email;

  @Column(name = "nick_name")
  @NotNull
  @NotEmpty
  private String nickname;

  @Column(name = "user_type")
  @NotNull
  @Enumerated(EnumType.ORDINAL)
  private USER_TYPE type;

  @Column(name = "password")
  @NotEmpty
  private String password;
  @Column(name = "description",columnDefinition = "VARCHAR(3000) DEFAULT ''")
  @NotNull
  @Length( max = 3000)
  private String description="";

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_pic")
  private Media ProfilePicture = null;

  public User(String session, String nick, USER_TYPE userType) {
    this.sessionID = session;
    this.nickname = nick;
    this.type = userType;
  }

  public User(String uuid) {
    this(uuid, null, USER_TYPE.UNAUTHENTICATED);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(this.type.name()));
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Getter
  @AllArgsConstructor
  public enum USER_TYPE {
    ADMIN(0),
    PREMIUM(1),
    AUTHENTICATED(2),
    UNAUTHENTICATED(3),
    ;
    private int _nmType;
  }

  //    @Min(0)
  //    @Max(2)

  @Override
  public boolean equals(Object o) {
    log.info("copmaring " + this.toString() + "with " + o.toString());
    if (!(o instanceof User)) {
      log.info("wrong instance: " + o.getClass());
      return false;
    }
    User user = (User) o;
    if (this.type == USER_TYPE.UNAUTHENTICATED) {
      return Objects.equals(this.sessionID, user.sessionID);
    }
    return this.id == user.id;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this.sessionID);
    //        return
    // this.type==USER_TYPE.UNAUTHENTICATED?Objects.hashCode(this.sessionID):Objects.hashCode(this.id);
  }

  @Override
  public int compareTo(@org.jetbrains.annotations.NotNull Object o) {

    if (!(o instanceof User)) {
      throw new ClassCastException(
          "This class can only be compared with other classes derived from User class, not with "
              + o.getClass());
    }
    User user = (User) o;
    int res = this.getUserType().compareTo(user.getUserType());
    return res;
  }

  public USER_TYPE getUserType() {
    return type;
  }
}
