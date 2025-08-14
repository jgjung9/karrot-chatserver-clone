package karrot.chat.chatserver.infra.redis.stream;

import karrot.chat.chatserver.config.EmbeddedRedisConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(EmbeddedRedisConfig.class)
class RedisStreamProducerTest {

    @Autowired
    private RedisStreamProducer redisStreamProducer;

    @Autowired
    private RedisTemplate redisTemplate;

    @AfterEach
    void cleanRedisDb() {
        redisTemplate
                .getConnectionFactory()
                .getConnection()
                .serverCommands()
                .flushDb();
    }

    @Test
    void sendToStream() {
        String streamKey = "chat-stream";
        String now = LocalDateTime.now().toString();
        Map<String, String> messageBody = Map.of(
                "chatId", "1",
                "senderId", "1",
                "message", "안녕!",
                "createAt", now
        );
        redisStreamProducer.sendToStream(streamKey, messageBody);

        List<MapRecord<String, Object, Object>> records = redisTemplate
                .opsForStream()
                .range(streamKey, Range.closed("0", "+"));

        assertNotNull(records);
        assertFalse(records.isEmpty());

        MapRecord<String, Object, Object> record = records.get(records.size() - 1); // 마지막 메시지
        Map<Object, Object> fields = record.getValue();


        assertThat("1").isEqualTo(fields.get("chatId"));
        assertThat("1").isEqualTo(fields.get("senderId"));
        assertThat("안녕!").isEqualTo(fields.get("message"));
        assertThat(now).isEqualTo(fields.get("createAt"));
    }
}