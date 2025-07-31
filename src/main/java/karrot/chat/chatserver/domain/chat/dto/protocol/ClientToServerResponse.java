package karrot.chat.chatserver.domain.chat.dto.protocol;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClientToServerResponse {
    private String command;
    private boolean success;
    private String message;
    private LocalDateTime sentAt;
}
