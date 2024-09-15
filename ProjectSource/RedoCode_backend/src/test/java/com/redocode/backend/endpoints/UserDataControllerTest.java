package com.redocode.backend.endpoints;

import com.redocode.backend.Messages.AccountInfoMessage;
import com.redocode.backend.Messages.Authentication.Authentication;
import com.redocode.backend.Messages.Authentication.AuthenticationRequest;
import com.redocode.backend.Secuirity.JwtService;
import com.redocode.backend.database.User;
import com.redocode.backend.database.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@Slf4j
class UserDataControllerTest {

  @LocalServerPort private int port;

  @Autowired private TestRestTemplate restTemplate;

  @Autowired JwtService jwtService;

  @Autowired UsersRepository usersRepository;

  @Autowired PasswordEncoder passwordEncoder;

  private final String _exisitingPass = "Password+123";

  String getFullEndpoint(String endpoint) {
    String endpointResutl = "http://localhost:" + port + endpoint;
    log.info("endpoint get for: " + endpointResutl);
    return endpointResutl;
  }

  private final String _loginEndPont = "/public/auth/login";

  @Test
  void getAccountInfo() {

    assertNotNull(restTemplate);
    User exisitingUser = usersRepository.findAll().get(0);

    AuthenticationRequest authenticationRequest =
        AuthenticationRequest.builder()
            .email(exisitingUser.getEmail())
            .password(_exisitingPass)
            .build();
    Authentication response =
        this.restTemplate.postForObject(
            getFullEndpoint(_loginEndPont), authenticationRequest, Authentication.class);
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + response.getToken());
    HttpEntity<String> entity = new HttpEntity<>(headers);
    AccountInfoMessage info =
        ((ResponseEntity<AccountInfoMessage>)
                this.restTemplate.exchange(
                    getFullEndpoint("/secure/user/info"),
                    HttpMethod.GET,
                    entity,
                    AccountInfoMessage.class))
            .getBody();

    assertEquals(exisitingUser.getNickname(), info.getNickname());
    assertEquals(exisitingUser.getUserType(), info.getType());
    assertEquals(exisitingUser.getEmail(), info.getMail());
    assertEquals(
        exisitingUser.getProfilePicture() == null ? "" : exisitingUser.getProfilePicture().getUrl(),
        info.getProfilePicture());
  }
}
