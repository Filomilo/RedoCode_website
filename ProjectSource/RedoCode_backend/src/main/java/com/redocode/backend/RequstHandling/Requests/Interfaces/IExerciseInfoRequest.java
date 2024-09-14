package com.redocode.backend.RequstHandling.Requests.Interfaces;

import org.jetbrains.annotations.NotNull;

public interface IExerciseInfoRequest {
  public void setTitle(@NotNull String title);

  public void setDescription(@NotNull String description);

  public String getTitle();

  public String getDescription();
}
