package karrot.chat.chatserver.infra.redis.stream;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class RedisStreamProducer {

    private final RedisTemplate<String, String> redisTemplate;

    public void sendToStream(String streamKey, Map<String, String> messageBody) {
        log.info("message body: {}", messageBody);

        redisTemplate.opsForStream()
                .add(StreamRecords.newRecord()
                        .in(streamKey)
                        .ofMap(messageBody)
                        .withId(RecordId.autoGenerate())
                );
    }
}
