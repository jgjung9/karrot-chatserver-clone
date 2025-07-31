package karrot.chat.chatserver.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    @Bean
    @Primary
    public JPAQueryFactory accountQueryFactory(
            @Qualifier("accountEntityManager") EntityManager accountEntityManager
    ) {
        return new JPAQueryFactory(accountEntityManager);
    }

    @Bean
    public JPAQueryFactory chatQueryFactory(
            @Qualifier("chatEntityManager") EntityManager chatEntityManager
    ) {
        return new JPAQueryFactory(chatEntityManager);
    }
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}
