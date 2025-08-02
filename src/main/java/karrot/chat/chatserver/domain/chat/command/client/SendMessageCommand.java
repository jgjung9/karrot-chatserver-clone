package karrot.chat.chatserver.domain.chat.command.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import karrot.chat.chatserver.domain.chat.command.client.ClientCommand;
import karrot.chat.chatserver.domain.chat.dto.payload.SendMessageRequest;
import karrot.chat.chatserver.domain.chat.dto.protocol.ClientToServerResponse;
import karrot.chat.chatserver.infra.redis.stream.RedisStreamProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SendMessageCommand implements ClientCommand {

    private final ObjectMapper objectMapper;
    private final RedisStreamProducer redisStreamProducer;

    @Override
    public void execute(JsonNode payload, WebSocketSession session) throws IOException {
        SendMessageRequest sendMessageRequest = objectMapper.treeToValue(payload, SendMessageRequest.class);

        LocalDateTime createAt = LocalDateTime.now();
        redisStreamProducer.sendToStream("chat-stream", Map.of(
                "userId", sendMessageRequest.getUserId().toString(),
                "chatId", sendMessageRequest.getChatId().toString(),
                "message", sendMessageRequest.getMessage(),
                "createAt", createAt.toString()
        ));

        ClientToServerResponse response = new ClientToServerResponse();
        response.setCommand("SEND_MESSAGE");
        response.setSuccess(true);
        response.setMessage("성공");
        response.setSentAt(createAt);
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(response)));
    }

    @Override
    public ClientCommands getCommandType() {
        return ClientCommands.SEND_MESSAGE;
    }
}
