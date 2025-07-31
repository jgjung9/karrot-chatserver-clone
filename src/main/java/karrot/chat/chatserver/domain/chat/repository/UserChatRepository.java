package karrot.chat.chatserver.domain.chat.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import karrot.chat.chatserver.domain.chat.entity.UserChat;
import karrot.chat.chatserver.domain.chat.entity.UserChatId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static karrot.chat.chatserver.domain.chat.entity.QUserChat.userChat;

@Repository
@Transactional("chatTransactionManager")
public class UserChatRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public UserChatRepository(
            @Qualifier("chatEntityManager") EntityManager em,
            @Qualifier("chatQueryFactory") JPAQueryFactory queryFactory
    ) {
        this.em = em;
        this.queryFactory = queryFactory;
    }

    // 복합 키를 사용하므로 사전에 id 값들이 다 채워져 있어야 한다.
    public UserChatId save(UserChat userChat) {
        em.merge(userChat);
        return userChat.getUserChatId();
    }

    public UserChat findById(UserChatId userChatId) {
        return queryFactory
                .selectFrom(userChat)
                .where(
                        userChat.chatId.eq(userChatId.getChatId()),
                        userChat.userId.eq(userChatId.getUserId())
                )
                .fetchFirst();

    }

    public List<UserChat> findAllByUserId(Long userId) {
        return queryFactory
                .selectFrom(userChat)
                .where(userChat.userId.eq(userId))
                .orderBy(userChat.displayIdx.desc())
                .fetch();
    }
}
