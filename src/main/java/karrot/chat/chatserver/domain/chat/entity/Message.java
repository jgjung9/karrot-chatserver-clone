package karrot.chat.chatserver.domain.chat.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    private Long userId;

    private String text;

    private LocalDateTime time;

    public static Message createMessage(Chat chat, Long userId, String text) {
        Message message = new Message();
        message.chat = chat;
        message.userId = userId;
        message.text = text;
        message.time = LocalDateTime.now();
        return message;
    }
}
