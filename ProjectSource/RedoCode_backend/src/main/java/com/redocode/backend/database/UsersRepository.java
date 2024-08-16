package com.redocode.backend.database;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Long> {
  User findByNickname(String userName);

  User findByEmail(String email);
}
