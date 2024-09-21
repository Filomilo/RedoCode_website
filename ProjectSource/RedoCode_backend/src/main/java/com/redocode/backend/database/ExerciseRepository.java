package com.redocode.backend.database;

import com.redocode.backend.Messages.LanguageUsePart;
import com.redocode.backend.Messages.StatisticMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
          + " e   left JOIN  e.solutions left join e.ratings left join e.attempts")
  List<ExcersizeListEntry> getSimpleExcersizeList();

}

