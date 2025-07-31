package karrot.chat.chatserver.domain.chat.dto.protocol;

import lombok.Data;

@Data
public class ServerToClientRequest {
    private String command;
    private String body;
}
