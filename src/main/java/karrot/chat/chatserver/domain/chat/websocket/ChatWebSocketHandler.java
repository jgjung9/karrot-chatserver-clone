package karrot.chat.chatserver.domain.chat.websocket;

import karrot.chat.chatserver.domain.chat.command.CommandManager;
import karrot.chat.chatserver.domain.chat.session.SessionManager;
import karrot.chat.chatserver.infra.redis.repository.UserSessionRepository;
import karrot.chat.chatserver.infra.redis.session.UserSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatWebSocketHandler extends TextWebSocketHandler {

    @Value("${SERVER_NUMBER}")
    private String serverNumber;

    private final SessionManager sessionManager;
    private final CommandManager commandManager;
    private final UserSessionRepository userSessionRepository;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = (String) session.getAttributes().get("userId");
        userSessionRepository.save(new UserSession(userId, serverNumber));
        sessionManager.register(Long.valueOf(userId), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        commandManager.execute(session, message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String userId = (String) session.getAttributes().get("userId");
        sessionManager.remove(Long.valueOf(userId));
        log.info("연결 종료: {}", userId);
    }
}
