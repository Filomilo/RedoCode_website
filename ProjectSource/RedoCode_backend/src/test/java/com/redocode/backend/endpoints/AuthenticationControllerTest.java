package com.redocode.backend.endpoints;

import com.redocode.backend.Messages.Authentication.AuthenticationRequest;
import com.redocode.backend.Messages.Authentication.Authentication;
import com.redocode.backend.Messages.Authentication.RegisterRequest;
import com.redocode.backend.Secuirity.JwtService;
import com.redocode.backend.database.User;
import com.redocode.backend.database.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@Slf4j
// @Disabled("Islotating specific test for debugging")
class AuthenticationControllerTest {

  @LocalServerPort private int port;

  @Autowired private TestRestTemplate restTemplate;

  @Autowired JwtService jwtService;

  @Autowired UsersRepository usersRepository;

  @Autowired PasswordEncoder passwordEncoder;

  private final String _registerEndPont = "/public/auth/register";
  private final String _loginEndPont = "/public/auth/login";

  // localhost:8080/public/auth/register
  String getFullEndpoint(String endpoint) {
    String endpointResutl = "http://localhost:" + port + endpoint;
    log.info("endpoint get for: " + endpointResutl);
    return endpointResutl;
  }

  private final String _correctMail = "email@email.com";
  private final String _correctPass = "Password+123";
  private final String _correctNick = "Nick";

  private final String _exisitingtMail = "sunny@mail.com";
  private final String _exisitingPass = "Password+123";
  private final String _exisitngNick = "sunny";

  @Test
  void registerUser() {
    assertNotNull(restTemplate);

    RegisterRequest registerRequest =
        RegisterRequest.builder()
            .email(_correctMail)
            .password(_correctPass)
            .nickname(_correctNick)
            .build();

    User user =
        User.builder()
            .email(_correctMail)
            .password(_correctPass)
            .nickname(_correctNick)
            .type(User.USER_TYPE.AUTHENTICATED)
            .build();

    Authentication response =
        this.restTemplate.postForObject(
            getFullEndpoint(_registerEndPont), registerRequest, Authentication.class);

    assertEquals(user.getEmail(), jwtService.extractUsername(response.getToken()));

    User newUser = usersRepository.findByEmail(_correctMail);

    assertEquals(user.getEmail(), newUser.getEmail());

    assertEquals(user.getNickname(), newUser.getNickname());

    assertEquals(User.USER_TYPE.AUTHENTICATED, newUser.getType());
  }

  @Test
  void login() {
    assertNotNull(restTemplate);

    AuthenticationRequest authenticationRequest =
        AuthenticationRequest.builder().email(_exisitingtMail).password(_exisitingPass).build();

    User user =
        User.builder()
            .email(_exisitingtMail)
            .password(_exisitingPass)
            .nickname(_exisitngNick)
            .type(User.USER_TYPE.AUTHENTICATED)
            .build();

    Authentication response =
        this.restTemplate.postForObject(
            getFullEndpoint(_loginEndPont), authenticationRequest, Authentication.class);

    assertEquals(user.getEmail(), jwtService.extractUsername(response.getToken()));
  }

  @Test
  void IncorrectLogin() {
    assertNotNull(restTemplate);

    AuthenticationRequest authenticationRequest =
        AuthenticationRequest.builder().email("aaaa").password(_exisitingPass).build();
    Authentication response =
        this.restTemplate.postForObject(
            getFullEndpoint(_loginEndPont), authenticationRequest, Authentication.class);
    assertNull(response);
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "  ", "Test@aaaa", "-----.com", _exisitingtMail})
  void IncorrectRegisterMail(String mail) {
    assertNotNull(restTemplate);

    RegisterRequest registerRequest =
        RegisterRequest.builder().email(mail).password(_correctPass).nickname(_correctNick).build();
    Authentication response =
        this.restTemplate.postForObject(
            getFullEndpoint(_registerEndPont), registerRequest, Authentication.class);
    assertNull(response);
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "  "})
  void IncorrectRegisterNick(String nick) {
    assertNotNull(restTemplate);

    RegisterRequest registerRequest =
        RegisterRequest.builder().email(_correctMail).password(_correctPass).nickname(nick).build();
    Authentication response =
        this.restTemplate.postForObject(
            getFullEndpoint(_registerEndPont), registerRequest, Authentication.class);
    assertNull(response);
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "",
        "                     ",
        "123",
        "12345678901234567890",
        "password+123",
        "Passssssssword",
        "Passsssssword123",
        "Passssssword_______",
        "Admin+123"
      })
  void IncorrectRegisterPassword(String pass) {
    assertNotNull(restTemplate);

    RegisterRequest registerRequest =
        RegisterRequest.builder().email(_correctMail).password(pass).nickname(_correctNick).build();
    Authentication response =
        this.restTemplate.postForObject(
            getFullEndpoint(_registerEndPont), registerRequest, Authentication.class);
    assertNull(response);
  }
}
