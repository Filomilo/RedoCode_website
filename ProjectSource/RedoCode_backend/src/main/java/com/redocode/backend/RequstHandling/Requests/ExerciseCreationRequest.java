package com.redocode.backend.RequstHandling.Requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExerciseCreationRequest extends CodeTestRequest {
  @NotNull String Title;
  @NotNull String Description;
}
