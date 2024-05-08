package com.zhonghai.drawingapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration  // 声明这是一个配置类
@EnableWebSocketMessageBroker  // 启用WebSocket消息代理
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
        // 注册一个WebSocket端点，客户端将使用它来连接到WebSocket服务器。
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 使用SockJS，使得即使浏览器不支持原生WebSocket也可以使用。
        registry.addEndpoint("/paint").withSockJS();
//        registry.addEndpoint("/api/board/{boardId}/ws").withSockJS();
    }

    @Override
        // 配置消息代理，它将用于路由消息。
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 指定客户端订阅路径的前缀("/topic")
        registry.enableSimpleBroker("/topic");
        // 指定客户端发送消息的路径的前缀("/app")
        registry.setApplicationDestinationPrefixes("/app");
    }
}