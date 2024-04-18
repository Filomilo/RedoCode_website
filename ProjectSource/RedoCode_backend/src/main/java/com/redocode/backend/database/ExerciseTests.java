package com.redocode.backend.database;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import jakarta.persistence.*;
import javassist.compiler.ast.Variable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="exercise_tests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseTests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "excersize")
    private Excersize excersize;

    @JsonProperty("input")
    String input;
    @JsonProperty("expected_output")
    String  expectedOutput;

}
