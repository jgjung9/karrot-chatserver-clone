package karrot.chat.chatserver.domain.chat.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import karrot.chat.chatserver.domain.chat.entity.Message;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static karrot.chat.chatserver.domain.chat.entity.QMessage.message;

@Repository
@Transactional("chatTransactionManager")
public class MessageRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public MessageRepository(
            @Qualifier("chatEntityManager") EntityManager em,
            @Qualifier("chatQueryFactory") JPAQueryFactory queryFactory
    ) {
        this.em = em;
        this.queryFactory = queryFactory;
    }

    public Long save(Message message) {
        if (message.getId() == null) {
            em.persist(message);
        } else {
            em.merge(message);
        }
        return message.getId();
    }

    public Message findById(Long id) {
        return queryFactory
                .selectFrom(message)
                .where(message.id.eq(id))
                .fetchFirst();
    }

    public List<Message> findAllByChatId(Long chatId) {
        return queryFactory
                .selectFrom(message)
                .where(message.chatId.eq(chatId))
                .fetch();
    }
}
