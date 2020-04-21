package org.r.server.websocket;

import org.r.server.websocket.server.WebSocketServer;
import org.r.server.websocket.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class WebsocketApplication implements CommandLineRunner {

    @Autowired
    private WebSocketServer webSocketServer;


    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(WebsocketApplication.class, args);
        SpringUtil.init(applicationContext);
    }

    @Override
    public void run(String... args) throws Exception {
        webSocketServer.start();
    }
}
