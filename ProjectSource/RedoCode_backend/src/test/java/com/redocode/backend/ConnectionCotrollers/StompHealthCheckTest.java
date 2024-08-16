package com.redocode.backend.ConnectionCotrollers;

import com.redocode.backend.WebSocketTestBase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;

// @RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Disabled("tet do not owtk when run along sie other for currently uknonwn reason")
class StompHealthCheckTest extends WebSocketTestBase {

  WebSocketStompClient stompClient;
  static final String WEBSOCKET_TOPIC_HEALTH_RESPONSE = "/user/public/topic/health";
  static final String WEBSOCKET_TOPIC_HEALTH_DESTIN = "/public/app/public/Health";

  @LocalServerPort int port;

  @Override
  protected String getWebSocketUri() {
    return getWebSocketUri(port);
  }

  @BeforeEach
  public void setup() {

    assertDoesNotThrow(
        () -> {
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
