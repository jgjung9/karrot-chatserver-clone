package karrot.chat.chatserver.domain.chat.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import karrot.chat.chatserver.domain.chat.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static karrot.chat.chatserver.domain.chat.entity.QMessage.message;

@Repository
@RequiredArgsConstructor
public class MessageRepositoryImpl implements MessageRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Message> findAllByChatId(Long chatId) {
        return queryFactory
                .selectFrom(message)
                .where(message.chat.id.eq(chatId))
                .fetch();
    }
}
