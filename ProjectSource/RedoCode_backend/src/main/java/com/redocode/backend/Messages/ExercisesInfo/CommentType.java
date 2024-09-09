package com.redocode.backend.Messages.ExercisesInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Value
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(force = true)
public class CommentType {
  @NonNull @NotBlank String nickname;
  @NonNull String profilePicture;
  @NonNull @NotBlank String comment;
}
