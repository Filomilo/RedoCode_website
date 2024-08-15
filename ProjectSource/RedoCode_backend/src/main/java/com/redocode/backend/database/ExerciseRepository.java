package com.redocode.backend.database;

import com.redocode.backend.Messages.ExerciseListRequestMessage;
import org.hibernate.query.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Excersize,Long> {

    @Query( "SELECT distinct   new com.redocode.backend.database.ExcersizeListEntry (e )  FROM Excersize e   left JOIN  e.solutions left join e.ratings left join e.attempts")
    List<ExcersizeListEntry> getSimpleExcersizeList();
//    @Query("SELECT e FROM MyEntity e WHERE e.someProperty = :somePropertyValue AND e.anotherProperty = :anotherPropertyValue")
//    List<ExcersizeListEntry> findBySomePropertyAndAnotherPropertyNamed(@Param("somePropertyValue") String somePropertyValue, @Param("anotherPropertyValue") String anotherPropertyValue);

//    @Query("select distinct COUNT (e ) from  Excersize e left JOIN fetch e.languages left join e.ratings left join e.attempts LIMIT 10")
//    List<?> getExeciseListFromQuery(@Param("req")ExerciseListRequestMessage req);




//    Query("select u from User u where u.firstname = :#{#customer.firstname}")
//    List<User> findUsersByCustomersFirstname(@Param("customer") Customer customer);
}
