package com.redocode.backend.Messages.ExercisesInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(force = true)
public class SolutionsData {
    @NonNull
    @NotBlank
    String title;
    @NonNull
    @NotBlank
    String desc;
    long maxExecutionTimeMs;
    @NonNull
    @NotEmpty
    @JsonProperty("solutionList")
    List<SolutionItemList> solutionList;
    @NonNull
    List<CommentType> comments;
}
