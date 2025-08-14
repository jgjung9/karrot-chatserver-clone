package karrot.chat.chatserver.domain.chat.service;

import karrot.chat.chatserver.common.Result;
import karrot.chat.chatserver.domain.chat.entity.Chat;
import karrot.chat.chatserver.domain.chat.entity.Message;
import karrot.chat.chatserver.domain.chat.entity.UserChat;
import karrot.chat.chatserver.domain.chat.repository.ChatRepository;
import karrot.chat.chatserver.domain.chat.repository.MessageRepository;
import karrot.chat.chatserver.domain.chat.repository.UserChatRepository;
import karrot.chat.chatserver.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MessageServiceTest {

    @Autowired
    MessageService messageService;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    UserChatRepository userChatRepository;

    @Test
    @DisplayName("정상적으로 메시지 생성")
    void save() throws Exception {
        // given
        Chat chat = chatRepository.save(new Chat());
        UserChat userChat = userChatRepository.save(UserChat.createUserChat(chat, 1L, false, ""));

        // when
        Result<Message> saveResult = messageService.save(chat.getId(), 1L, "Hello");

        // then
        Message saved = saveResult.getData();
        Message found = messageRepository.findById(saveResult.getData().getId()).get();
        assertThat(saved).isEqualTo(found);
    }

    @Test
    @DisplayName("존재하지 않는 채팅방에 메시지 생성 ErrorCode.CHAT_NOT_FOUND")
    void chatNotFound() throws Exception {
        Result<Message> messageResult = messageService.save(1L, 1L, "Hello");

        ErrorCode errorCode = ErrorCode.ofCode(messageResult.getErrorCode());

        assertThat(errorCode).isEqualTo(ErrorCode.CHAT_NOT_FOUND);
    }

    @Test
    @DisplayName("채팅방에 접속해 있지 않은 유저가 메시지 생성 ErrorCode.CHAT_NOT_CONTAINS_USER")
    void chatNotContainsUser() throws Exception {
        Chat chat = chatRepository.save(new Chat());

        Result<Message> saveResult = messageService.save(chat.getId(), 1L, "Hello");
        ErrorCode errorCode = ErrorCode.ofCode(saveResult.getErrorCode());

        assertThat(errorCode).isEqualTo(ErrorCode.CHAT_NOT_CONTAINS_USER);
    }
}