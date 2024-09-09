package com.redocode.backend.database;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ExcersizeDiffucultyRatingId implements Serializable {

  private Long user;
  private Long excersize;
}
