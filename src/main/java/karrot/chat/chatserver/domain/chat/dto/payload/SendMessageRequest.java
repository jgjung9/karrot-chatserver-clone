package karrot.chat.chatserver.domain.chat.dto.payload;

import lombok.Data;

@Data
public class SendMessageRequest {
    private Long userId;
    private Long chatId;
    private String message;
}
