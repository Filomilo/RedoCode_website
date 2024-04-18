package com.redocode.backend.database;


import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.Range;

import java.sql.Time;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Entity
@Table(name ="excersizes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "excersize_languages",
            joinColumns = @JoinColumn(name = "excersize_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private Set<ProgrammingLanguage> languages = new HashSet<>();


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
    private Set<ExerciseTests> exerciseTests = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "excersize")
    private Set<SolutionPrograms> solutions = new HashSet<>();
}
