package com.redocode.backend.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface SolutionProgramsRepository extends JpaRepository<SolutionPrograms, Long> {


  SolutionPrograms findFirstByLanguageIdAndExcersizeId(
     Long languageId,  Long exerciseId);
}
