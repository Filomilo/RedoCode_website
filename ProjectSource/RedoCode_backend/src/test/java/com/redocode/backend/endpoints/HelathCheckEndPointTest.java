package com.redocode.backend.endpoints;

import com.redocode.backend.Messages.Authentication.AuthenticationRequest;
import com.redocode.backend.Messages.Authentication.Authentication;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.*;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration
//@Disabled("Islotating specific test for debugging")
public class HelathCheckEndPointTest {


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void hello() {
        assertNotNull(restTemplate);
        assertEquals("hello", (String) this.restTemplate.getForObject("http://localhost:" + port + "/public/healthcheck/hello", String.class));
    }

    @Test
    public void helloSecuredUnauthetnicated() {
        assertNotNull(restTemplate);
        assertNull(this.restTemplate.getForObject("http://localhost:" + port + "/secure/healthcheck/hello", String.class));
    }


    private final String _exisitingtMail = "sunny@mail.com";
    private final String _exisitingPass = "Password+123";
    private final String _exisitngNick = "sunny";
    private final String _registerEndPont = "/public/auth/register";
    private final String _loginEndPont = "/public/auth/login";

    //localhost:8080/public/auth/register
    String getFullEndpoint(String endpoint) {
        String endpointResutl = "http://localhost:" + port + endpoint;
        return endpointResutl;
    }

    @Test
    public void helloSecuredAuthenticated() {


        assertNotNull(restTemplate);

        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email(_exisitingtMail)
                .password(_exisitingPass)
                .build();
        Authentication response = this.restTemplate.postForObject(getFullEndpoint(_loginEndPont), authenticationRequest, Authentication.class);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + response.getToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        assertEquals("hello secret", ((ResponseEntity<String>) this.restTemplate.exchange(getFullEndpoint("/secure/healthcheck/hello"), HttpMethod.GET, entity, String.class)).getBody());
    }


}