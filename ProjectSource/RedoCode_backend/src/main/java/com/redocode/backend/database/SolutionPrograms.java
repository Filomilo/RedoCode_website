package com.redocode.backend.database;

import jakarta.persistence.*;

@Entity
@Table(name ="Solution_programs")
public class SolutionPrograms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String code;
    @OneToOne
    ProgrammingLanguage language;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "excersize_id")
    @Id
    private Excersize excersize;
}
