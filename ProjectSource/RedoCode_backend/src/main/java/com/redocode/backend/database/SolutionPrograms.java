package com.redocode.backend.database;

import jakarta.persistence.*;

@Entity
@Table(name ="Solution_programs")
public class SolutionPrograms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String code;
    @OneToOne
    ProgrammingLanguage language;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "excersize_id")
    private Excersize excersize;
}
