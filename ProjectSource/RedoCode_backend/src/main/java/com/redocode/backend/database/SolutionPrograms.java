package com.redocode.backend.database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name ="Solution_programs")
@ToString
@Getter
@Setter
public class SolutionPrograms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    @ManyToOne
    private ProgrammingLanguage language;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "excersize")
    @ToString.Exclude
    private Excersize excersize;


}
