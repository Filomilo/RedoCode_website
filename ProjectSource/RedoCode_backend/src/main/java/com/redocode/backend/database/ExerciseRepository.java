package com.redocode.backend.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Excersize,Long> {
    /*
    @Query(value = "SELECT excersizes.id as id,excersizes.excersize_name as name, 'C++' as langauge, 3 as difficulty, attempts as popularity   FROM(\n" +
            "SELECT excersize_id , count(exercise_attempts.id) as attempts\n" +
            "FROM exercise_attempts, excersizes\n" +
            "WHERE exercise_attempts.excersize_id=excersizes.id\n" +
            "GROUP BY excersize_id), excersizes, users\n" +
            "WHERE excersizes.id=excersize_id\n" +
            "AND excersizes.author_id=users.id")


    */
    /*
    new com.redocode.backend.database.ExcersizeListEntry (e )
    @Query("SELECT new com.redocode.backend.database.ExcersizeListEntry (s.ex_id , e.excersizeName , e.languages ,5, s.attempts )  FROM ( select e.id AS ex_id, COUNT(a.id) AS attempts FROM Excersize e, ExerciseAttempts a Where e.id=a.excersize.id GROUP BY e.id) s, Excersize e JOIN e.languages where e.id = s.ex_id ")
    public List<ExcersizeListEntry> getSimpleExcersizeList();
    JOIN FETCH e.languages JOIN fetch e.author join fetch e.attempts

*/
    @Query( "SELECT distinct   new com.redocode.backend.database.ExcersizeListEntry (e )  FROM Excersize e   left JOIN fetch e.languages left join e.ratings left join e.attempts")
    public List<?> getSimpleExcersizeList();
}
