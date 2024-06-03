package com.redocode.backend.database;

import org.springframework.data.jpa.repository.JpaRepository;
import com.redocode.backend.database.User;
public interface UsersRepository extends JpaRepository<User,Long> {
    User findByUserName(String userName);

}
