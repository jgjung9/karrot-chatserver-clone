package karrot.chat.chatserver.domain.chat.repository;

import karrot.chat.chatserver.domain.chat.entity.Message;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional("chatTransactionManager")
class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Test
    void save() {
        Message message = new Message();
        message.setChatId(1L);
        message.setUserId(1L);
        message.setText("Hello");
        message.setTime(LocalDateTime.now());

        Long savedId = messageRepository.save(message);
        assertThat(savedId).isEqualTo(message.getId());
    }

    @Test
    void findById() {
        Message message = new Message();
        message.setChatId(1L);
        message.setUserId(1L);
        message.setText("Hello");
        message.setTime(LocalDateTime.now());

        Long savedId = messageRepository.save(message);
        Message found = messageRepository.findById(savedId);

        assertThat(found).isEqualTo(message);
    }

    @Test
    void findAllByChatId() {
        Long chatId = 1L;
        Message message1 = new Message();
        message1.setChatId(chatId);
        message1.setUserId(1L);
        message1.setText("Hello");
        message1.setTime(LocalDateTime.now());
        messageRepository.save(message1);

        Message message2 = new Message();
        message2.setChatId(chatId);
        message2.setUserId(1L);
        message2.setText("Hello");
        message2.setTime(LocalDateTime.now());
        messageRepository.save(message2);

        List<Message> messages = messageRepository.findAllByChatId(chatId);
        assertThat(messages).contains(message1, message2);
    }
}