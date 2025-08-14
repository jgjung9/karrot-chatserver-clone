package karrot.chat.chatserver.domain.chat.repository;

import karrot.chat.chatserver.domain.chat.entity.Chat;
import karrot.chat.chatserver.domain.chat.entity.Message;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ChatRepository chatRepository;
    private Chat insertedChat;

    @BeforeEach
    void setUp() {
        insertedChat = chatRepository.save(new Chat());
    }

    @Test
    @DisplayName("채팅방의 메시지 목록 가져오기")
    void findAllByChatId() {
        Message message1 = Message.createMessage(insertedChat, 1L, "Hello1");
        messageRepository.save(message1);

        Message message2 = Message.createMessage(insertedChat, 1L, "Hello2");
        messageRepository.save(message2);

        List<Message> messages = messageRepository.findAllByChatId(insertedChat.getId());
        assertThat(messages).contains(message1, message2);
    }
}