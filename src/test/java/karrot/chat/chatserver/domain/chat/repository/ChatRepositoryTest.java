package karrot.chat.chatserver.domain.chat.repository;

import karrot.chat.chatserver.domain.chat.entity.Chat;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional("chatTransactionManager")
class ChatRepositoryTest {

    @Autowired
    private ChatRepository chatRepository;

    @Test
    void save() {
        Chat chat = new Chat();
        chat.setTitle("test chat");
        chat.setThumbnailId(123L);

        Long savedId = chatRepository.save(chat);
        assertThat(savedId).isEqualTo(chat.getId());
    }

    @Test
    void findById() {
        Chat chat = new Chat();
        chat.setTitle("test chat");
        chat.setThumbnailId(123L);

        Long savedId = chatRepository.save(chat);
        Chat found = chatRepository.findById(savedId);
        assertThat(found).isEqualTo(chat);
    }
}