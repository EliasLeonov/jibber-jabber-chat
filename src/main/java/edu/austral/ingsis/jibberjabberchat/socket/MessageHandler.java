package edu.austral.ingsis.jibberjabberchat.socket;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public interface MessageHandler<T> {

    void handle(WebSocketSession session, T message) throws IOException;

}
