package com.redocode.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redocode.backend.Messages.Authentication.AuthenticationRequest;
import com.redocode.backend.Messages.Authentication.Authentication;
import com.redocode.backend.database.User;
import com.redocode.backend.database.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Slf4j
@Disabled("tests wiht websokcet  dont wokt along side")
class CustomHandshakeHandlerTest  {


    @LocalServerPort
    int port;
    @Autowired
            private RedoCodeController redoCodeController;
    @Autowired
            private UsersRepository usersRepository;
    @Autowired
    private TestRestTemplate restTemplate;

    final static ObjectMapper objectMapper = new ObjectMapper();

    private final String _exisitingtMail="sunny@mail.com";
    private final String _exisitingPass="Password+123";
    private final String _exisitngNick="sunny";
    private final String _loginEndPont="/public/auth/login";
    String getFullEndpoint(String endpoint)
    {
        String endpointResutl= "http://localhost:"+port+endpoint;
        log.info("endpoint get for: "+ endpointResutl);
        return  endpointResutl;
    }

    WebSocketStompClient stompClient;
    static final String WEBSOCKET_TOPIC = "/public/topic";
    static final String WEBSOCKET_URI = "ws://localhost:8080/public/web-socket";
    BlockingQueue<String> blockingQueue;
    @BeforeEach
    public void setup() {

        blockingQueue = new LinkedBlockingDeque<>();
        stompClient =  new WebSocketStompClient(new SockJsClient(
                asList(new WebSocketTransport(new StandardWebSocketClient()))
        ));
    }

    @Test
    public void userShouldBeregistered() throws Exception {

        AuthenticationRequest authenticationRequest= AuthenticationRequest.builder()
                .email(_exisitingtMail)
                .password(_exisitingPass)
                .build();



        Authentication response= this.restTemplate.postForObject(getFullEndpoint(_loginEndPont), authenticationRequest, Authentication.class);

        log.info(response.toString());




        StompSession session = stompClient
                .connect(WEBSOCKET_URI, new StompSessionHandlerAdapter() {})
                .get(1, SECONDS);
        log.info(Arrays.toString(redoCodeController.connectedUsers.values().toArray()));
        session.send("/public/app/tokenAuth",objectMapper.writeValueAsBytes(Authentication.builder().token(response.getToken()).build()));
        Thread.sleep(500);
        assertEquals(1, redoCodeController.connectedUsers.size());
        log.info("connected users: "+ Arrays.toString(redoCodeController.connectedUsers.values().toArray()));
        User[] userValues=new User[redoCodeController.connectedUsers.size()];
        redoCodeController.connectedUsers.values().toArray(userValues);
        assertEquals(_exisitingtMail,userValues[0].getEmail());


    }
    @Test
    public void userShouldBeUnathenticated() throws Exception {






        WebSocketHttpHeaders stompHeaders=new WebSocketHttpHeaders();
//        stompHeaders.add("token",response.getToken());

        StompSession session = stompClient
                .connect(WEBSOCKET_URI,stompHeaders, new StompSessionHandlerAdapter() {})
                .get(1, SECONDS);
        log.info(Arrays.toString(redoCodeController.connectedUsers.values().toArray()));

        assertEquals(1, redoCodeController.connectedUsers.size());
        log.info("connected users: "+ Arrays.toString(redoCodeController.connectedUsers.values().toArray()));
        User[] userValues=new User[redoCodeController.connectedUsers.size()];
        redoCodeController.connectedUsers.values().toArray(userValues);
        assertEquals(User.USER_TYPE.UNAUTHENTICATED,userValues[0].getType());


    }

}