package com.redocode.backend.database;

import com.redocode.backend.Messages.LanguageUsePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

  SolutionPrograms findFirstByExcersizeIdAndSolutionAuthorId(
      Long exerciseId, Long solutionAuthorId);

  int countAllByExcersizeId(Long exerciseId);

  int countAllByExcersizeIdAndAvgExecutionTimeGreaterThan(Long exerciseId, Long avgExecutionTime);

  @Query(
      """
      SELECT new com.redocode.backend.Messages.LanguageUsePart(pl.name, COUNT(e))
        FROM SolutionPrograms sp
        LEFT JOIN Excersize e ON sp.excersize.id = e.id
        LEFT JOIN ProgrammingLanguage pl ON sp.language.id = pl.id
        WHERE sp.solutionAuthor.id = :authorId
        AND e.author.id != :authorId
        GROUP BY pl.name
      """)
  List<LanguageUsePart> findLanguageAmountForExercsieNotMadeThisUser(
      @Param("authorId") Long authorId);

  List<SolutionPrograms> findAllBySolutionAuthorId(Long author);
}
