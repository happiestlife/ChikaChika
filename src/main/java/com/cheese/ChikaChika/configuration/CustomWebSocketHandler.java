package com.cheese.ChikaChika.configuration;

import com.cheese.ChikaChika.consts.Consts;
import com.cheese.ChikaChika.consts.Message;
import com.cheese.ChikaChika.consts.ResponseStatus;
import com.cheese.ChikaChika.model.Teeth;
import com.cheese.ChikaChika.service.TeethBrushedService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
public class CustomWebSocketHandler extends TextWebSocketHandler {

    private final TeethBrushedService teethBrushedService;
    
    private WebSocketSession session;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("[{}] Web socket connected!", session.getId());

        this.session = session;
        teethBrushedService.resetBrushedInfo();

        String firstBrushSection = Consts.BRUSH_SECTION.UF_OUTSIDE;
        Teeth teeth = new Teeth(ResponseStatus.SUCCESS.getLevel(), Message.getMessage(Message.Group.NEXT_BRUSH_SECTION, firstBrushSection),
                "start", Consts.BRUSH_LEVEL.NONE, firstBrushSection);

        ObjectMapper objectMapper = new ObjectMapper();
        String messageBody = objectMapper.writeValueAsString(teeth);
        WebSocketMessage message = new TextMessage(messageBody);

        session.sendMessage(message);

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
