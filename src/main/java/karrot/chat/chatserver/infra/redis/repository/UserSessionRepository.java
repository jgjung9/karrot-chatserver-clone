package karrot.chat.chatserver.infra.redis.repository;

import karrot.chat.chatserver.infra.redis.session.UserSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class UserSessionRepository {

    private final RedisTemplate redisTemplate;

    public void save(UserSession session) {
        redisTemplate.opsForValue().set(
                "session:user:" + session.getUserId(),
                session.getServerNumber(),
                Duration.ofMillis(300000)
        );
    }
}
