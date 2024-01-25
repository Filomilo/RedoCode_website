package com.redocode.backend.database;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Exercise_Attempts")
public class ExerciseAttempts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(
            name = "Excersize_id"
    )
    Excersize excersize;

    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    User user;

    @Column(name = "started_at")
    Date startedAt;

    @Column(name = "finished_at")
    Date finishedAt;

    @Column(name = "succes")
    private Boolean succes;



}
