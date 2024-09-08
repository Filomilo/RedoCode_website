package com.redocode.backend.database;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "Solution_programs")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolutionPrograms {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 30000)
  private String code;

  @ManyToOne private ProgrammingLanguage language;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "excersize")
  @ToString.Exclude
  private Excersize excersize;

  @NotNull
  @Column(name = "Avg_execution_time")
  @Min(1)
  private Long AvgExecutionTime;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "solution_author")
  private User SolutionAuthor;
}
