package com.redocode.backend.Configs;


import com.redocode.backend.CustomHandshakeHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/public/topic");
        config.setApplicationDestinationPrefixes("/public/app");

//        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/public/web-socket")
                .setAllowedOriginPatterns("*")
                .setHandshakeHandler(new CustomHandshakeHandler())
                .addInterceptors()

        //  .withSockJS() //there were problems when connecting iwth vue

        ;
        registry.addEndpoint("/public/web-socket")
                .setAllowedOriginPatterns("*")
                .setHandshakeHandler(new CustomHandshakeHandler())
                .addInterceptors()

                .withSockJS() //there were problems when connecting iwth vue

        ;
    }


}