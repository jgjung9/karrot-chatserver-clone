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
        Map<String, String> messageBody = Map.of(
                "roomId", "room1",
                "senderId", "user123",
                "message", "안녕!",
                "targetUserId", "77"
        );
        redisStreamProducer.sendToStream(streamKey, messageBody);

        List<MapRecord<String, Object, Object>> records = redisTemplate
                .opsForStream()
                .range(streamKey, Range.closed("0", "+"));

        assertNotNull(records);
        assertFalse(records.isEmpty());

        MapRecord<String, Object, Object> record = records.get(records.size() - 1); // 마지막 메시지
        Map<Object, Object> fields = record.getValue();


        assertThat("room1").isEqualTo(fields.get("roomId"));
        assertThat("user123").isEqualTo(fields.get("senderId"));
        assertThat("안녕!").isEqualTo(fields.get("message"));
        assertThat("77").isEqualTo(fields.get("targetUserId"));
    }
}