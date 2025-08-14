package karrot.chat.chatserver.domain.chat.repository;

import karrot.chat.chatserver.domain.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long>, ChatRepositoryCustom {

}
