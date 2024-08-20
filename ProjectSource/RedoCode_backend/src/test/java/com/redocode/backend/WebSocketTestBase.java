package com.redocode.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
public abstract class WebSocketTestBase {
  WebSocketStompClient stompClient;
  static final String WEBSOCKET_URI = "ws://localhost:8080/web-socket";

  protected BlockingQueue<String> blockingQueue;
  protected StompSession session;

  protected String getWebSocketUri(int port) {
    return "ws://localhost:" + port + "/public/web-socket";
  }

  protected abstract String getWebSocketUri();

  protected static final ObjectMapper mapper = new ObjectMapper();

  protected void setup() throws ExecutionException, InterruptedException, TimeoutException {
    TimeUnit.SECONDS.sleep(2);
    blockingQueue = new LinkedBlockingDeque<>();
    stompClient =
        new WebSocketStompClient(
            new SockJsClient(List.of(new WebSocketTransport(new StandardWebSocketClient()))));
    log.info("connecting to web socket: " + getWebSocketUri());
    session =
        stompClient.connect(getWebSocketUri(), new StompSessionHandlerAdapter() {}).get(5, SECONDS);

    log.info("connected: " + session.isConnected());
    log.info("session: " + session.getSessionId());
  }

  protected void tearDown() throws ExecutionException, InterruptedException, TimeoutException {
    log.info("tearing down");
    session.disconnect();
    stompClient.stop();
    stompClient = null;
    TimeUnit.SECONDS.sleep(3);
  }

  protected void subscribe(String source) {
    log.info("subscribing to " + source);
    StompSession.Subscription subscription =
        session.subscribe(source, new DefaultStompFrameHandler());
    log.info("subscribed to " + source);
    log.info("status: " + subscription.getSubscriptionHeaders());
  }

  class DefaultStompFrameHandler extends StompSessionHandlerAdapter {
    @Override
    public Type getPayloadType(StompHeaders stompHeaders) {

      return byte[].class;
    }

    @Override
    public void handleFrame(StompHeaders stompHeaders, Object o) {
      log.info("connected: " + stompClient.isRunning());
      log.info("received frame from web socket: " + new String((byte[]) o));
      blockingQueue.offer(new String((byte[]) o));
      System.out.println("QUEUE STRUCTURE: "+Arrays.toString(blockingQueue.toArray()));
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
      super.afterConnected(session, connectedHeaders);
      log.info("\n\n\n\nconnected: " + session.isConnected() + "\n\n\n\n\n");
    }

    @Override
    public void handleException(
        StompSession session,
        StompCommand command,
        StompHeaders headers,
        byte[] payload,
        Throwable exception) {
      super.handleException(session, command, headers, payload, exception);
      log.error("STompClinet error: " + exception.getMessage());
    }
  }
}
