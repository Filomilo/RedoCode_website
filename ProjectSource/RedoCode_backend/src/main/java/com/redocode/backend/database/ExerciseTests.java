package com.redocode.backend.database;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redocode.backend.Tools.RedoCodeObjectMapper;
import com.redocode.backend.VmAcces.CodeRunners.Variables.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Entity
@Table(name = "exercise_tests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Slf4j
public class ExerciseTests {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "excersize")
  @ToString.Exclude
  private Excersize excersize;

  @JsonProperty("input")
  @Column(length = 10000)
  String input;

  @JsonProperty("expectedOutput")
  @Column(length = 10000)
  String expectedOutput;

  public Variables getParsedInput(Variables.VARIABLES_TYPES inputType)
      throws JsonProcessingException {

    ObjectMapper mapper = new ObjectMapper();
    return RedoCodeObjectMapper.parseVaraibles(this.getInput(), inputType);
  }

  public Variables getParsedOutput(Variables.VARIABLES_TYPES outputType)
      throws JsonProcessingException {
    if(this.getExpectedOutput()==null)
      return null;
    return RedoCodeObjectMapper.parseVaraibles(this.getExpectedOutput(), outputType);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ExerciseTests that = (ExerciseTests) o;
    return Objects.equals(id, that.id)
        && Objects.equals(input, that.input)
        && Objects.equals(expectedOutput, that.expectedOutput);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, input, expectedOutput);
  }
}
