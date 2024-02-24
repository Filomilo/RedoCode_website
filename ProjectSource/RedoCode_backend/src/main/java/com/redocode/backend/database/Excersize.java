package com.redocode.backend.database;


import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name ="excersizes")
@Data
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


    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Set<ProgrammingLanguage> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<ProgrammingLanguage> languages) {
        this.languages = languages;
    }

    public Set<ExcersizeDiffucultyRating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<ExcersizeDiffucultyRating> ratings) {
        this.ratings = ratings;
    }

    public Set<ExerciseAttempts> getAttempts() {
        return attempts;
    }

    public void setAttempts(Set<ExerciseAttempts> attempts) {
        this.attempts = attempts;
    }
}
