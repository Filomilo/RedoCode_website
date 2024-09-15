package com.redocode.backend.database;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MediaRepository  extends JpaRepository<Media, UUID> {
}
