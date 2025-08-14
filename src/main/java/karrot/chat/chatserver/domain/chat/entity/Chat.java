package karrot.chat.chatserver.domain.chat.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long id;

    private String title;

    @Column(name = "thumbnail_id")
    private Long thumbnailId;

    @Enumerated(value = EnumType.STRING)
    private ChatType type;

    @OneToMany(mappedBy = "chat")
    private List<UserChat> userChats = new ArrayList<>();

    public static Chat createChat(String title, Long thumbnailId) {
        Chat chat = new Chat();
        chat.title = title;
        chat.thumbnailId = thumbnailId;
        return chat;
    }

    // 연관 관계 편의 메서드
    public void addUserChat(UserChat userChat) {
        userChats.add(userChat);
    }

}
