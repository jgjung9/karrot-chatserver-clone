package karrot.chat.chatserver.domain.chat.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(indexes = {
        @Index(name = "idx_user_chat_chat_id", columnList = "chat_id"),
        @Index(name = "idx_user_chat_user_id", columnList = "user_id")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserChat {
    @Id
    @GeneratedValue
    @Column(name = "user_chat_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    private Long userId;

    private Boolean mute;

    @Column(name = "display_idx")
    private String displayIdx;

    public static UserChat createUserChat(Chat chat, Long userId, boolean mute, String displayIdx) {
        UserChat userChat = new UserChat();
        userChat.changeChat(chat);
        userChat.userId = userId;
        userChat.mute = mute;
        userChat.displayIdx = displayIdx;
        return userChat;
    }

    // 연관 관계 편의 메서드
    public void changeChat(Chat chat) {
        if (this.chat != null) {
            this.chat.getUserChats().remove(this);
        }

        this.chat = chat;
        chat.addUserChat(this);
    }

}
