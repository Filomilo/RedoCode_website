package com.redocode.backend.database;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExcersizeDiffucultyRatingRepository
    extends JpaRepository<ExcersizeDiffucultyRating, ExcersizeDiffucultyRatingId> {
  public ExcersizeDiffucultyRating findFirstByUserIdAndExcersizeId(Long userId, Long exerciseId);
}
