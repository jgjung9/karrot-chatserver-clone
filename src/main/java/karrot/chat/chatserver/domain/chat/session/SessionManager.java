package karrot.chat.chatserver.domain.chat.session;

import jakarta.annotation.PreDestroy;
import karrot.chat.chatserver.infra.redis.repository.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.PingMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
@RequiredArgsConstructor
public class SessionManager {

    private final UserSessionRepository userSessionRepository;
    private final Map<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @PreDestroy
    public void shutdown() throws IOException {
        for (Long userId : sessions.keySet()) {
            remove(userId);
        }
    }

    public void register(Long userId, WebSocketSession session) {
        sessions.put(userId, session);
    }

    public WebSocketSession getSession(Long userId) {
        return sessions.get(userId);
    }

    public void remove(Long userId) throws IOException {
        userSessionRepository.delete(userId);
        WebSocketSession session = sessions.get(userId);
        sessions.remove(userId);
        session.close();
    }

    @Scheduled(fixedDelay = 5000)
    public void sendPingToAll() throws IOException {
        for (Long userId : sessions.keySet()) {
            WebSocketSession session = sessions.get(userId);
            if (session.isOpen()) {
                try {
                    session.sendMessage(new PingMessage(ByteBuffer.wrap("ping".getBytes())));
                } catch (Exception e) {
                    log.warn("ping 전송 실패 userId = {}", userId);
                    remove(userId);
                }
            } else {
                remove(userId);
            }
        }
    }
}
