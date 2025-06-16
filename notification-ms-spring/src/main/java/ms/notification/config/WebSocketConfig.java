package ms.notification.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

//@Configuration
//@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	@Value("${ms.core.notification.ws.path:http://localhost:63342}")
	private String websocketPath;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/message");  // Prefix for broadcasting messages
        config.setApplicationDestinationPrefixes("/app");  // Prefix for client-to-server communication
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")  // This is the WebSocket endpoint
                .setAllowedOrigins(websocketPath)  // Allow frontend origin (localhost:63342)
                .withSockJS();  // Enable SockJS for fallback support
    }
}