package com.redocode.backend.database;


import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.java.Log;
import org.apache.commons.lang3.Range;

import java.sql.Time;
import java.util.*;

@Entity
@Table(name ="excersizes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Log
public class Excersize {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(
            name = "excersize_name"
    )
    String excersizeName;
    @Column(
            name = "ram_Mb"
    )
    Integer ram_mb;

    @Column(
            name = "time_for_task"
    )
    Time timeForTask;
    @Column(
            name = "amount_of_auto_tests"
    )
    int amountOfAutoTests;
    @Column(
            name = "array_x_length_range_min"
    )
    Integer arrayXLengthRange;
    @Column(
            name = "array_x_length_range_max"
    )
    Integer arrayXLengthRangeMax;
    @Column(
            name = "array_y_length_range_min"
    )
    Integer arrayYLengthRangeMin;
    @Column(
            name = "array_y_length_range_max"
    )
    Integer arrayYLengthRangeMax;
    @Column(
            name = "value_range_min"
    )
    Float valueLengthRangeMin;
    @Column(
            name = "value_range_max"
    )
    Float valueLengthRangeMax;
    @Column(
            name = "string_format_mask"
    )
    Byte stringFormat;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "excersize")
    private Set<ExcersizeDiffucultyRating> ratings = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "excersize" )
    private Set<ExerciseAttempts> attempts = new HashSet<>();

    @Column(
            name = "description"
    )
    private String description;

    Variables.VARIABLES_TYPES inputType;
    Variables.VARIABLES_TYPES outputType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "excersize")
    private List<ExerciseTests> exerciseTests = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "excersize")
    private Set<SolutionPrograms> solutions = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Excersize excersize = (Excersize) o;
        return Objects.equals(id, excersize.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Excersize{" +
                "id=" + id +
                ", excersizeName='" + excersizeName + '\'' +
                ", ram_mb=" + ram_mb +
                ", timeForTask=" + timeForTask +
                ", amountOfAutoTests=" + amountOfAutoTests +
                ", arrayXLengthRange=" + arrayXLengthRange +
                ", arrayXLengthRangeMax=" + arrayXLengthRangeMax +
                ", arrayYLengthRangeMin=" + arrayYLengthRangeMin +
                ", arrayYLengthRangeMax=" + arrayYLengthRangeMax +
                ", valueLengthRangeMin=" + valueLengthRangeMin +
                ", valueLengthRangeMax=" + valueLengthRangeMax +
                ", stringFormat=" + stringFormat +
                ", author=" + author +
                ", ratings=" + ratings +
                ", attempts=" + attempts +
                ", description='" + description + '\'' +
                ", inputType=" + inputType +
                ", outputType=" + outputType +
                ", exerciseTests=" + exerciseTests +
                ", solutions=" + solutions +
                '}';
    }

    public Set<ProgrammingLanguage> getLanguages() {
        HashSet<ProgrammingLanguage> languages= new HashSet<>();
        log.info(" progmriang solutions: "+ Arrays.toString(this.solutions.toArray()));
        for (SolutionPrograms solutionPrograms: this.getSolutions()
             ) {
            languages.add(solutionPrograms.getLanguage());
        }
        return languages;
    }
}
