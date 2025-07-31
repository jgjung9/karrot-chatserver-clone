package karrot.chat.chatserver.domain.chat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.checkerframework.checker.units.qual.C;

@Entity
@Data
public class UserChat {
    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @Id
    @Column(name = "user_id")
    private Long userID;

    private Boolean mute;

    @Column(name = "display_idx")
    private String displayIdx;
}
