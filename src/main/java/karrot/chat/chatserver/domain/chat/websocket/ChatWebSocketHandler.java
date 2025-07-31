package karrot.chat.chatserver.domain.chat.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import karrot.chat.chatserver.domain.chat.command.CommandManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private Long sequence = 0L;
    private final Map<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final CommandManager commandManager;
    private final ObjectMapper objectMapper;

    public ChatWebSocketHandler(CommandManager commandManager, ObjectMapper objectMapper) {
        this.commandManager = commandManager;
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(++sequence, session);
        log.info("userid: {} session: {} 연결 완료", sequence, session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        commandManager.execute(session, message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Long userId = 1L;
        sessions.remove(userId);
        log.info("연결 종료: {}", userId);
    }
}
