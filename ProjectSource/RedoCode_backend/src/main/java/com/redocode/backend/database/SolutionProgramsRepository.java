package com.redocode.backend.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SolutionProgramsRepository extends JpaRepository<SolutionPrograms, Long> {

  @Query(
      "SELECT sp FROM SolutionPrograms sp WHERE sp.language.id = :languageId AND sp.excersize.id ="
          + " :exerciseId")
  SolutionPrograms findByLanguageIdAndExerciseId(
      @Param("languageId") Long languageId, @Param("exerciseId") Long exerciseId);
}
