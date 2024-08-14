package com.redocode.backend.database;


import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.java.Log;
import com.redocode.backend.Messages.UtilContainers.Range;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import com.redocode.backend.database.RangeNumberType;

import java.sql.SQLType;
import java.sql.Time;
import java.util.*;

import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "excersizes")
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
    @NotNull
    String excersizeName;
    @Column(
            name = "ram_Mb"
    )
    Integer ram_mb;

    @Column(
            name = "time_for_task_min"
    )
    @NotNull
    Long timeForTaskMin;
    @Column(
            name = "amount_of_auto_tests"
    )
    @NotNull
    int amountOfAutoTests;
    @Column(
            name = "array_x_length_range_min"
    )
    Integer arrayXLengthRangeMin;
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
    @NotNull
    Float valueLengthRangeMax;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "excersize", cascade = CascadeType.ALL)
    private Set<ExcersizeDiffucultyRating> ratings = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "excersize", cascade = CascadeType.ALL)
    private Set<ExerciseAttempts> attempts = new HashSet<>();

    @Column(
            name = "description"
    )
    @NotNull
    private String description;

    Variables.VARIABLES_TYPES inputType;
    Variables.VARIABLES_TYPES outputType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "excersize", cascade = CascadeType.ALL)
    private List<ExerciseTests> exerciseTests = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "excersize", cascade = CascadeType.ALL)
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

    @Column(
            name = "break_character_input"
    )
    Boolean breakCharacterInput = false;
    @Column(
            name = "lower_case_input"
    )
    Boolean lowerCaseInput = false;
    @Column(
            name = "number_input"
    )
    Boolean numberInput = false;
    @Column(
            name = "space_input"
    )
    Boolean spaceInput = false;
    @Column(
            name = "special_character_input"
    )
    Boolean specialCharacterInput = false;
    @Column(
            name = "upper_case_input"
    )
    Boolean upperCaseInput = false;

    @Column(
            name = "max_execution_time_ms"
    )
    @NotNull
    Long maxExecutionTimeMS;

    public Set<ProgrammingLanguage> getLanguages() {
        HashSet<ProgrammingLanguage> languages = new HashSet<>();
        log.info(" progmriang solutions: " + Arrays.toString(this.solutions.toArray()));
        for (SolutionPrograms solutionPrograms : this.getSolutions()
        ) {
            languages.add(solutionPrograms.getLanguage());
        }
        return languages;
    }

    public Range getLengthRange() {
        return new Range(valueLengthRangeMin, valueLengthRangeMax);
    }

    public Range getXArrayRange() {
        return new Range(arrayXLengthRangeMin, arrayXLengthRangeMax);
    }

    public Range getYArrayRange() {
        return new Range(arrayXLengthRangeMin, arrayYLengthRangeMax);
    }
}
