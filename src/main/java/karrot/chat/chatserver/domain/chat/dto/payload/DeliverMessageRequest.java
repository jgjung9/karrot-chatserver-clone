package karrot.chat.chatserver.domain.chat.dto.payload;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class DeliverMessageRequest {
    private Long receiverId;
    private Long chatId;
    private Long senderId;
    private String message;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdAt;
}
