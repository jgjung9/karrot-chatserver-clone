package karrot.chat.chatserver.domain.chat.service;

import karrot.chat.chatserver.common.Result;
import karrot.chat.chatserver.domain.chat.dto.payload.SendMessageRequest;
import karrot.chat.chatserver.domain.chat.entity.Chat;
import karrot.chat.chatserver.domain.chat.entity.Message;
import karrot.chat.chatserver.domain.chat.entity.UserChat;
import karrot.chat.chatserver.domain.chat.repository.ChatRepository;
import karrot.chat.chatserver.domain.chat.repository.MessageRepository;
import karrot.chat.chatserver.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MessageService {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

    @Transactional
    public Result<Message> save(Long chatId, Long userId, String text) {
        Optional<Chat> found = chatRepository.findById(chatId);
        if (found.isEmpty()) {
            return Result.failure(ErrorCode.CHAT_NOT_FOUND);
        }
        Chat chat = found.get();
        Optional<UserChat> userChat = chat.getUserChats().stream()
                .filter(uc -> uc.getUserId().equals(userId))
                .findFirst();
        if (userChat.isEmpty()) {
            return Result.failure(ErrorCode.CHAT_NOT_CONTAINS_USER);
        }

        Message message = Message.createMessage(found.get(), userId, text);
        messageRepository.save(message);
        return Result.success(message);
    }

}
