package karrot.chat.chatserver.domain.chat.dto.payload;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class PushMessageRequest {
    private Long chatId;
    private Long userId;
    private String message;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime sentAt;
}
