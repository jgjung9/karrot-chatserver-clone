package karrot.chat.chatserver.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatDataSourceConfig {

    @Bean
    public JPAQueryFactory chatQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }
}
