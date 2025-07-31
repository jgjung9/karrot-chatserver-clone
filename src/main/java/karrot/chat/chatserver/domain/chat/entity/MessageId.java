package karrot.chat.chatserver.domain.chat.entity;

import jakarta.persistence.Column;

import java.io.Serializable;
import java.util.Objects;

public class MessageId implements Serializable {
    @Column(name = "chat_id")
    private Long chatId;
    @Column(name = "user_id")
    private Long userId;

    public MessageId() {}

    public MessageId(Long chatId, Long userId) {
        this.chatId = chatId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageId messageId = (MessageId) o;
        return Objects.equals(chatId, messageId.chatId) && Objects.equals(userId, messageId.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, userId);
    }
}
