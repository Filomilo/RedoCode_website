package com.redocode.backend.ConnectionCotrollers;

import com.redocode.backend.WebSocketTestBase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class StompHealthCheckTest extends WebSocketTestBase {
    WebSocketStompClient stompClient;
    static final String WEBSOCKET_TOPIC_HEALTH_RESPONSE = "/user/topic/health";
    static final String WEBSOCKET_TOPIC_HEALTH_DESTIN = "/app/Health";
    @BeforeEach
    public void setup() {

        assertDoesNotThrow(()->{
            super.setup();
        });

    }

    @Test
    public void healthCheck() throws Exception {
        subscribe(WEBSOCKET_TOPIC_HEALTH_RESPONSE);
        String message = "MESSAGE TEST";
        session.send(WEBSOCKET_TOPIC_HEALTH_DESTIN, message.getBytes());
        assertEquals(message, blockingQueue.poll(1, SECONDS));
    }




}