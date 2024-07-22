package com.redocode.backend.database;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redocode.backend.VmAcces.CodeRunners.Variables.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Entity
@Table(name ="exercise_tests")
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
    String  expectedOutput;


    private Variables parseVaraibles(String toParse, Variables.VARIABLES_TYPES type) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Variables input=null;
        switch (type) {
            case SINGLE_INTEGER -> {input=mapper.readValue(toParse, SingleInteger.class);
            }
            case SINGLE_STRING -> {input=mapper.readValue(toParse, SingleString.class);
            }
            case SINGLE_FLOAT -> {input=mapper.readValue(toParse, SingleFloat.class);
            }
            case ARRAY_OF_INTEGERS -> {input=mapper.readValue(toParse, ArrayOfIntegers.class);
            }
            case ARRAY_STRINGS -> {input=mapper.readValue(toParse, ArrayOfStrings.class);
            }
            case ARRAY_OF_FLOATS -> {input=mapper.readValue(toParse, ArrayOfFloats.class);
            }
            case DOUBLE_ARRAY_OF_INTEGERS -> {input=mapper.readValue(toParse, DoubleArrayOfIntegers.class);
            }
            case DOUBLE_ARRAY_OF_FLOATS -> {input=mapper.readValue(toParse, DoubleArrayOfFloats.class);
            }
            case DOUBLE_ARRAY_OF_STRINGS -> {input=mapper.readValue(toParse, DoubleArrayOfStrings.class);
            }
        }
        return input;
    }


   public Variables getParsedInput(Variables.VARIABLES_TYPES inputType) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
       return parseVaraibles(this.getInput(),inputType);
    }

    public Variables getParsedOutput(Variables.VARIABLES_TYPES outputType) throws JsonProcessingException {
        return parseVaraibles(this.getExpectedOutput(),outputType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseTests that = (ExerciseTests) o;
        return Objects.equals(id, that.id) && Objects.equals(input, that.input) && Objects.equals(expectedOutput, that.expectedOutput);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, input, expectedOutput);
    }
}
