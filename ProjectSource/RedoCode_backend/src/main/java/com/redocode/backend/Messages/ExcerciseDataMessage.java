package com.redocode.backend.Messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Program.Factory.ProgramFactory;
import com.redocode.backend.VmAcces.CodeRunners.Program.Factory.StartingFunctions.StartingFuncitonGeneratorFactory;
import com.redocode.backend.VmAcces.CodeRunners.Program.Factory.StartingFunctions.StartingFunctionGenerator;
import com.redocode.backend.database.Excersize;
import com.redocode.backend.database.ExerciseTests;
import com.redocode.backend.database.ProgrammingLanguage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Value
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ExcerciseDataMessage {
    @JsonProperty
    List<String> availbleCodeRunners;
    @JsonProperty
    String title;
    @JsonProperty
    String desc;
    @JsonProperty
    String outputType;
    @JsonProperty
    String inputType;
    @JsonProperty
    Long id;
    @JsonProperty
    List<ExcerciseTestMessage> tests;
    @JsonProperty
    List<ExcerciseTestMessage> automaticTests;

    @JsonProperty
    Map<String,String> startingFunction;

    public ExcerciseDataMessage(Excersize excersize) throws JsonProcessingException {
        this.title=excersize.getExcersizeName();
        this.desc=excersize.getDescription();
        this.outputType= String.valueOf(excersize.getOutputType());
        this.inputType= String.valueOf(excersize.getInputType());
        this.id=excersize.getId();
        this.automaticTests=new ArrayList<>();
        availbleCodeRunners=new ArrayList<>();
        tests=new ArrayList<>();
        for (ExerciseTests tst: excersize.getExerciseTests()
             ) {
            tests.add(ExcerciseTestMessage.builder()
                            .input(tst.getParsedInput(excersize.getInputType()))
                            .expectedOutput(tst.getParsedOutput(excersize.getOutputType()))
                            .consoleOutput("")
                            .errorOutput("")
                    .build());
        }
        for (ProgrammingLanguage programmingLanguage:excersize.getLanguages()
             ) {
            availbleCodeRunners.add(programmingLanguage.getName());
        }
        startingFunction=new HashMap<>();
        for (String lang:availbleCodeRunners
             ) {
            startingFunction.put(lang, StartingFuncitonGeneratorFactory.getStartingFunctionGeneratorForLanguage(lang).getStartingFunction(excersize.getInputType(),excersize.getOutputType()));
        }
    }



}

