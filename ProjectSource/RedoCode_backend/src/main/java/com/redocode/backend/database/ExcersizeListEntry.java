package com.redocode.backend.database;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import java.util.Set;

@Data
@Getter
@Setter
@Log
public class ExcersizeListEntry {
  private Long id;
  private String name;
  private Set<ProgrammingLanguage> languages;
  private int difficulty;
  private Long popularity;
  private String description;

  public ExcersizeListEntry(Excersize e) {
    log.info("Exercise parsing: " + e);
    this.id = e.getId();
    this.name = e.getExcersizeName();
    this.languages = e.getLanguages();
    this.difficulty =
        e.getRatings().size() != 0
            ? e.getRatings().stream().mapToInt(element -> element.getRating()).sum()
                / e.getRatings().size()
            : 0;
    this.popularity = (long) e.getAttempts().size();
    this.description = e.getDescription();
  }
}
