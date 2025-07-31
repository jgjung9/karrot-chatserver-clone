package karrot.chat.chatserver.domain.chat.dto.protocol;

import lombok.Data;

@Data
public class ServerToClientResponse {
    boolean success;
    String message;
}
