package com.mochi.config;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class SocketConfig {
    @Bean
    public SocketIOServer socketIOServer(
            @Value("${socket.hostname}") String hostname,
            @Value("${socket.port}") int port
    ) {
        Configuration configuration = new Configuration();
        configuration.setHostname(hostname);
        configuration.setPort(port);
        SocketIOServer server = new SocketIOServer(configuration);
        System.out.println("Socket server is starting !!!");
        server.start();
        return server;
    }
}
