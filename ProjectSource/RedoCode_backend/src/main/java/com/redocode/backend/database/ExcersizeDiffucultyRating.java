package com.redocode.backend.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "Excersize_Diffuculty_Ratings")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@IdClass( ExcersizeDiffucultyRating.ExcersizeDiffucultyRatingId.class )
@Getter
public class ExcersizeDiffucultyRating {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @Id
  private User user;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "excersize_id")
  @Id
  private Excersize excersize;

  @Column(name = "rating", columnDefinition = "INT CHECK (rating >= 1 AND rating <= 5)")
  private int rating;

  @EqualsAndHashCode
  @NoArgsConstructor
  public class ExcersizeDiffucultyRatingId implements Serializable {

    private Long user;
    private Long excersize;
  }

}
