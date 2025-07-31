package karrot.chat.chatserver.domain.chat.session;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    private final Map<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void register(Long userId, WebSocketSession session) {
        sessions.put(userId, session);
    }

    public WebSocketSession getSession(Long userId) {
        return sessions.get(userId);
    }

    public void remove(Long userId) {
        sessions.remove(userId);
    }
}
