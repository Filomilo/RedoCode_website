package com.redocode.backend.database;

import java.util.Set;


public class ExcersizeListEntry {
    private  Long id;
    private  String name;
    private Set<ProgrammingLanguage> languages;
    private  int difficulty;
    private   Long popularity;


    public ExcersizeListEntry(Excersize e) {
        this.id = e.getId();
        this.name = e.getExcersizeName();
        this.languages = e.getLanguages();
        this.difficulty = e.getRatings().stream().mapToInt(element -> element.getRating()).sum()/e.getRatings().size();
        this.popularity = (long) e.getAttempts().size();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ProgrammingLanguage> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<ProgrammingLanguage> languages) {
        this.languages = languages;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public Long getPopularity() {
        return popularity;
    }

    public void setPopularity(Long popularity) {
        this.popularity = popularity;
    }
}
