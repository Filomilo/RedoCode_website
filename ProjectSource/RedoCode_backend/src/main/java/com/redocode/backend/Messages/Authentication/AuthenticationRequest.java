package com.redocode.backend.Messages.Authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class AuthenticationRequest {
  String email;
  String password;
}
