package com.redocode.backend;

import ch.qos.logback.core.net.ObjectWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeoutException;

import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
public abstract class WebSocketTestBase {
    WebSocketStompClient stompClient;
    static final String WEBSOCKET_URI = "ws://localhost:8080/web-socket";

    protected BlockingQueue<String> blockingQueue;
    protected StompSession session;

    protected String getWebSocketUri(int port)
    {
        return  "ws://localhost:"+ port+"/web-socket";
    }
    protected abstract String getWebSocketUri();
    protected static final ObjectMapper  mapper = new ObjectMapper();

   protected void setup() throws ExecutionException, InterruptedException, TimeoutException {

        blockingQueue = new LinkedBlockingDeque<>();
        stompClient =   new WebSocketStompClient(new SockJsClient(
                List.of(new WebSocketTransport(new StandardWebSocketClient()))));

        log.info("connecting to web socket: "+getWebSocketUri());
        session = stompClient
                .connect(getWebSocketUri() , new StompSessionHandlerAdapter() {})
                .get(1, SECONDS);
       log.info("connected: "+      session.isConnected());
       log.info("session: "+ session.getSessionId());

   }
    protected void tearDown() throws ExecutionException, InterruptedException, TimeoutException {
        session.disconnect();
        stompClient.stop();

    }


    protected void subscribe(String source)
    {
        log.info("subscribing to "+source);
        StompSession.Subscription subscription= session.subscribe(source, new DefaultStompFrameHandler());
        log.info("subscribed to "+source);
        log.info("status: "+ subscription.getSubscriptionHeaders() );
    }





    class DefaultStompFrameHandler extends StompSessionHandlerAdapter  {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {

            return byte[].class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            log.info("connected: "+ stompClient.isRunning());
            log.info("received frame from web socket: "+new String((byte[]) o));
            blockingQueue.offer(new String((byte[]) o));
        }
    }


}
