package com.redocode.backend.database;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgrammingLanguageRepository extends JpaRepository<ProgrammingLanguage, Long> {

  public ProgrammingLanguage findByName(String name);
}
