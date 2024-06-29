package com.redocode.backend;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
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

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Type;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Slf4j
class WebSocketConfigTest {
    WebSocketStompClient stompClient;
    static final String WEBSOCKET_TOPIC = "/topic";
    static final String WEBSOCKET_URI = "ws://localhost:8080/web-socket";
    BlockingQueue<String> blockingQueue;
    @BeforeEach
    public void setup() {

        blockingQueue = new LinkedBlockingDeque<>();
        stompClient =  new WebSocketStompClient(new SockJsClient(
                asList(new WebSocketTransport(new StandardWebSocketClient()))
        ));
    }

    @Test
    public void shouldReceiveAMessageFromTheServer() throws Exception {
        StompSession session = stompClient
                .connect(WEBSOCKET_URI, new StompSessionHandlerAdapter() {})
                .get(1, SECONDS);
         session.subscribe(WEBSOCKET_TOPIC, new DefaultStompFrameHandler());

        String message = "MESSAGE TEST";
        session.send(WEBSOCKET_TOPIC, message.getBytes());
//
        assertEquals(message, blockingQueue.poll(1, SECONDS));
    }


    class DefaultStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            log.info("getPayloadType: "+ stompHeaders.toString());
            return byte[].class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            log.info("handleFrame: "+ stompHeaders.toString());
            blockingQueue.offer(new String((byte[]) o));
        }
    }
}