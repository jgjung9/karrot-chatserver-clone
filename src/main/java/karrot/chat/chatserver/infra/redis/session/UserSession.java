package karrot.chat.chatserver.infra.redis.session;

import lombok.Data;

@Data
public class UserSession {
    private String userId;
    private String serverUrl;

    public UserSession(String userId, String serverUrl) {
        this.userId = userId;
        this.serverUrl = serverUrl;
    }
}
