package com.redocode.backend.endpoints;

import com.redocode.backend.Messages.Authentication.AuthenticationRequest;
import com.redocode.backend.Messages.Authentication.Authentication;
import com.redocode.backend.Messages.Authentication.RegisterRequest;
import com.redocode.backend.Secuirity.JwtService;
import com.redocode.backend.database.User;
import com.redocode.backend.database.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

@RestController
@Log
@RequiredArgsConstructor
@RequestMapping("/public/auth")
public class AuthenticationController {

  @Autowired private final PasswordEncoder passwordEncoder;
  @Autowired private final UsersRepository usersRepository;
  @Autowired private final JwtService jwtService;
  @Autowired private final AuthenticationManager authenticationManager;

  private static final Pattern pattern =
      Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.* ).{12,25}");

  @PostMapping("/register")
  public Authentication registerUser(@RequestBody RegisterRequest request) {

    if (!pattern.matcher(request.getPassword()).matches()) {
      throw new RuntimeException("Invalid password"); // todo: add own exception
    }
    if (usersRepository.findByEmail(request.getEmail()) != null) {
      throw new RuntimeException("User already exists");
    }

    User user =
        User.builder()
            .email(request.getEmail())
            .nickname(request.getNickname())
            .password(passwordEncoder.encode(request.getPassword()))
            .type(User.USER_TYPE.AUTHENTICATED)
            .build();
    usersRepository.save(user);
    String token = jwtService.generateToken(user);
    return Authentication.builder().token(token).build();
  }

  @PostMapping("/login")
  public Authentication login(@RequestBody AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    User user = usersRepository.findByEmail(request.getEmail());
    String token = jwtService.generateToken(user);
    return Authentication.builder().token(token).build();
  }
}
