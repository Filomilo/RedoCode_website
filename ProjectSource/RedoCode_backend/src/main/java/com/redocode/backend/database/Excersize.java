package com.redocode.backend.database;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name ="excersizes")
public class Excersize {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(
            name = "excersize_name"
    )
    String excersizeName;


    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToMany
    @JoinTable(
            name = "excersize_languages",
            joinColumns = @JoinColumn(name = "excersize_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private Set<ProgrammingLanguage> courses = new HashSet<>();


    @OneToMany
   private HashSet<ExcersizeDiffucultyRating> ratings;
    @OneToMany
    private HashSet<ExerciseAttempts> attempts;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExcersizeName() {
        return excersizeName;
    }

    public void setExcersizeName(String excersizeName) {
        this.excersizeName = excersizeName;
    }
}
