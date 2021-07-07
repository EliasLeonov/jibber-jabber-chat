package edu.austral.ingsis.jibberjabberchat.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

@Component
public class WebSocketListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketListener.class);

    @EventListener
    public void handleWebSocketConnect(SessionConnectedEvent event){
        logger.info("WS Connection established.");
    }

    @EventListener
    public void handleWebSocketSubscribe(SessionSubscribeEvent event){
        logger.info("WS subscription established.");
    }

    @EventListener
    public void handleWebSocketUnSubscribe(SessionUnsubscribeEvent event){
        logger.info("WS un-subscribed");
    }

    @EventListener
    public void handleWebSocketDisconnect(SessionDisconnectEvent event){
        logger.info("WS disconnected");
    }
}
