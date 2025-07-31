package karrot.chat.chatserver.domain.chat.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import karrot.chat.chatserver.domain.chat.entity.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static karrot.chat.chatserver.domain.chat.entity.QChat.chat;

@Repository
@Transactional("chatTransactionManager")
public class ChatRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public ChatRepository(
            @Qualifier("chatEntityManager") EntityManager em,
            @Qualifier("chatQueryFactory") JPAQueryFactory queryFactory
    ) {
        this.em = em;
        this.queryFactory = queryFactory;
    }

    public Long save(Chat chat) {
        if (chat.getId() == null) {
            em.persist(chat);
        } else {
            em.merge(chat);
        }
        return chat.getId();
    }

    public Chat findById(Long id) {
        return queryFactory
                .selectFrom(chat)
                .where(chat.id.eq(id))
                .fetchFirst();
    }
}
