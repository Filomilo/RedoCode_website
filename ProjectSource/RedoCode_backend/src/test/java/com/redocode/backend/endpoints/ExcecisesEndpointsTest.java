package com.redocode.backend.endpoints;

import com.redocode.backend.Secuirity.JwtService;
import com.redocode.backend.database.ExcersizeListEntry;
import com.redocode.backend.database.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@Slf4j
// @Disabled("Islotating specific test for debugging")
class ExcecisesEndpointsTest {

  @LocalServerPort private int port;

  @Autowired private TestRestTemplate restTemplate;

  @Autowired JwtService jwtService;

  @Autowired UsersRepository usersRepository;

  @Autowired PasswordEncoder passwordEncoder;

  @Test
  void getExceciseData() {
    assertNotNull(restTemplate);
    List<ExcersizeListEntry> excerciseDataMessage =
        restTemplate.getForObject(
            "http://localhost:" + port + "/public/exercises/list", List.class);

    assertNotNull(excerciseDataMessage);
  }
}
