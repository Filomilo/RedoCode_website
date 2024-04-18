package com.redocode.backend.database;


import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
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
