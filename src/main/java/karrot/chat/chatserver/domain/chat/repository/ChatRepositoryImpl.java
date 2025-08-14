package karrot.chat.chatserver.domain.chat.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatRepositoryImpl implements ChatRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
}
