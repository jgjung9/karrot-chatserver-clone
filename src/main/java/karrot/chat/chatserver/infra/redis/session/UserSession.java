package karrot.chat.chatserver.infra.redis.session;

import lombok.Data;

@Data
public class UserSession {
    private String userId;
    private String serverNumber;

    public UserSession(String userId, String serverNumber) {
        this.userId = userId;
        this.serverNumber = serverNumber;
    }
}
