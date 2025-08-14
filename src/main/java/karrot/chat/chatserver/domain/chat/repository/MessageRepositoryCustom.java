package karrot.chat.chatserver.domain.chat.repository;

import karrot.chat.chatserver.domain.chat.entity.Message;

import java.util.List;

public interface MessageRepositoryCustom {
    List<Message> findAllByChatId(Long chatId);
}
