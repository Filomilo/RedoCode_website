package com.redocode.backend.Messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redocode.backend.Tools.RedoCodeObjectMapper;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.database.Excersize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class CodeExerciseTab {
    String title;
    String description;
    Long id;
    List<CODE_RUNNER_TYPE> languages;
    public CodeExerciseTab(Excersize e)
    {
        this.id=e.getId();
    this.title=e.getExcersizeName();
    this.description=e.getDescription();
    this.languages=e.getLanguages().stream().map((x)-> RedoCodeObjectMapper.LanguageNameToCodeRunner(x.getName())).toList();
    }


}
