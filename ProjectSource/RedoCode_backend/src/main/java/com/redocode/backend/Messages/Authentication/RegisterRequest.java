package com.redocode.backend.Messages.Authentication;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RegisterRequest {
  @NotNull private String nickname;
  @NotNull private String password;
  @NotNull private String email;
}
