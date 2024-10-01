package com.redocode.backend.database;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Entity
@Table(name = "Media")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Slf4j
public class Media {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "uuid", insertable = false, updatable = false, nullable = false)
  UUID uuid;

  @NotNull byte[] data;
  @NotBlank @NotNull String extension;

  public String getUrl() {
    return this.uuid.toString() + "." + this.extension;
  }
}
