package karrot.chat.chatserver.domain.chat.websocket;

import karrot.chat.chatserver.domain.chat.command.CommandManager;
import karrot.chat.chatserver.domain.chat.session.SessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Slf4j
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private Long sequence = 0L;
    private final SessionManager sessionManager;
    private final CommandManager commandManager;

    public ChatWebSocketHandler(SessionManager sessionManager, CommandManager commandManager) {
        this.sessionManager = sessionManager;
        this.commandManager = commandManager;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionManager.register(++sequence, session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        commandManager.execute(session, message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Long userId = 1L;
        sessionManager.remove(userId);
        log.info("연결 종료: {}", userId);
    }
}
