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
          + " e   left JOIN  e.solutions left join e.ratings left join e.attempts")
  List<ExcersizeListEntry> getSimpleExcersizeList();
  //    @Query("SELECT e FROM MyEntity e WHERE e.someProperty = :somePropertyValue AND
  // e.anotherProperty = :anotherPropertyValue")
  //    List<ExcersizeListEntry>
  // findBySomePropertyAndAnotherPropertyNamed(@Param("somePropertyValue") String somePropertyValue,
  // @Param("anotherPropertyValue") String anotherPropertyValue);

  //    @Query("select distinct COUNT (e ) from  Excersize e left JOIN fetch e.languages left join
  // e.ratings left join e.attempts LIMIT 10")
  //    List<?> getExeciseListFromQuery(@Param("req")ExerciseListRequestMessage req);

  //    Query("select u from User u where u.firstname = :#{#customer.firstname}")
  //    List<User> findUsersByCustomersFirstname(@Param("customer") Customer customer);
}
