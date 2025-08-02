package karrot.chat.chatserver.domain.chat.dto.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageResponse {
    private LocalDateTime sentAt;
}
