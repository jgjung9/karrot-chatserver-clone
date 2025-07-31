package karrot.chat.chatserver.domain.account.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import karrot.chat.chatserver.domain.account.entity.Account;
import karrot.chat.chatserver.domain.account.entity.QAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static karrot.chat.chatserver.domain.account.entity.QAccount.account;

@Repository
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AccountRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public Long save(Account account) {
        if (account.getId() == null) {
            em.persist(account);
        } else {
            em.merge(account);
        }
        return account.getId();
    }

    public Account findById(Long id) {
        return queryFactory
                .selectFrom(account)
                .where(account.id.eq(id))
                .fetchFirst();
    }

    public Account findByUsername(String username) {
        return queryFactory
                .selectFrom(account)
                .where(account.username.eq(username))
                .fetchFirst();
    }
}
