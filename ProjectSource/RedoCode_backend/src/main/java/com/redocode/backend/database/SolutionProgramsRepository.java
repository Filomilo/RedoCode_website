package com.redocode.backend.database;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolutionProgramsRepository extends JpaRepository<SolutionPrograms, Long> {

  SolutionPrograms findFirstByLanguageIdAndExcersizeId(Long languageId, Long exerciseId);

  /**
   * sql query method that returns Solution by its code <br>
   * <br>
   * <b>Warrning</b> searching by entire solution code its not effective and this method should only
   * be used for testing purpuses only
   *
   * @param code Code wchich first occurance in database should be found
   * @return first SolutionPrograms entry in databse with provided code
   */
  SolutionPrograms findFirstByCode(String code);


  List<SolutionPrograms> findAllByExcersizeIdOrderByAvgExecutionTimeDesc(Long exerciseID);
  SolutionPrograms findFirstByExcersizeIdAndSolutionAuthorId(Long exerciseId, Long solutionAuthorId);
  int countAllByExcersizeId(Long exerciseId);
  int countAllByExcersizeIdAndAvgExecutionTimeGreaterThan(Long exerciseId, Long avgExecutionTime);
}
