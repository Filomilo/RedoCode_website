package com.redocode.backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class CustomHandshakeHandler extends DefaultHandshakeHandler {

  //    @Autowired
  //    private RedoCodeController redoCodeController;
  @Override
  protected Principal determineUser(
      ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
    String uuid = UUID.randomUUID().toString();
    request.getHeaders().getFirst("token");

    return new StompPrincipal(uuid);
  }
}
