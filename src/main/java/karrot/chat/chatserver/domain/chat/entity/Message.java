package karrot.chat.chatserver.domain.chat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Data
@IdClass(MessageId.class)
public class Message {
    @Id
    private Long chatId;
    @Id
    private Long userId;

    private String text;

    private LocalDateTime time;
}
