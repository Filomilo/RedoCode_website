package com.redocode.backend.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Excersize, Long> {
  /**
   * Exercise repository query that returns list of exercise in simple format prepared for show
   * casing on webiste exercises list
   *
   * @return exercises list containg list of solutions, rating and attempts
   */
  @Query(
      "SELECT distinct   new com.redocode.backend.database.ExcersizeListEntry (e )  FROM Excersize"
          + " e   left JOIN  e.solutions left join e.ratings")
  List<ExcersizeListEntry> getSimpleExcersizeList();
}
