package com.redocode.backend.database;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.extern.java.Log;

import java.util.Date;

@Entity
@Table(name = "Comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Log
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  @NonNull
  private User author;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "exercise_id")
  @NonNull
  private Excersize excersize;

  @NonNull @NotBlank private String comment;

  @NonNull
  private Date date= new Date();;
}
