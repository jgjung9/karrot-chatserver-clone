package karrot.chat.chatserver.domain.chat.repository;

import karrot.chat.chatserver.domain.chat.entity.UserChat;
import karrot.chat.chatserver.domain.chat.entity.UserChatId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional("chatTransactionManager")
class UserChatRepositoryTest {

    @Autowired
    private UserChatRepository userChatRepository;

    @Test
    void save() {
        UserChat userChat = new UserChat();
        userChat.setChatId(1L);
        userChat.setUserId(111111111111L);
        userChat.setMute(false);
        userChat.setDisplayIdx("12345");

        UserChatId savedId = userChatRepository.save(userChat);
        assertThat(savedId).isEqualTo(userChat.getUserChatId());
    }

    @Test
    void findById() {
        UserChat userChat = new UserChat();
        userChat.setChatId(1L);
        userChat.setUserId(111111111111L);
        userChat.setMute(false);
        userChat.setDisplayIdx("12345");

        UserChatId savedId = userChatRepository.save(userChat);
        UserChat found = userChatRepository.findById(savedId);
        assertThat(found).isEqualTo(userChat);
    }

    @Test
    void findAllByUserId() {
        Long userId = 10000000L;

        UserChat userChat1 = new UserChat();
        userChat1.setChatId(1L);
        userChat1.setUserId(userId);
        userChat1.setMute(false);
        userChat1.setDisplayIdx("12345");

        UserChat userChat2 = new UserChat();
        userChat2.setChatId(2L);
        userChat2.setUserId(userId);
        userChat2.setMute(false);
        userChat2.setDisplayIdx("12345");

        userChatRepository.save(userChat1);
        userChatRepository.save(userChat2);

        List<UserChat> chats = userChatRepository.findAllByUserId(userId);
        assertThat(chats).contains(userChat1, userChat2);
    }
}