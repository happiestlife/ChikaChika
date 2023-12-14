package com.cheese.ChikaChika.configuration;

import com.cheese.ChikaChika.model.Teeth;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
public class CustomWebSocketHandler extends TextWebSocketHandler {

    private WebSocketSession session;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("[{}] Web socket connected!", session.getId());

        this.session = session;

        super.afterConnectionEstablished(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("[{}] Client send message\n message >> {}", session.getId(), message.getPayload());

        super.handleTextMessage(session, message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("[{}] Web socket closed...", session.getId());

        session = null;

        super.afterConnectionClosed(session, status);
    }

    public boolean sendMessage(Teeth teeth){
        if(session == null) {
            return false;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String messageBody = objectMapper.writeValueAsString(teeth);
            WebSocketMessage message = new TextMessage(messageBody);

            session.sendMessage(message);
        } catch (Exception e){
            e.printStackTrace();
            log.info("Error occurred during send message to client...");
        }

        return true;
    }

}
