package karrot.chat.chatserver.domain.chat.command.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import karrot.chat.chatserver.common.Result;
import karrot.chat.chatserver.domain.chat.dto.payload.SendMessageRequest;
import karrot.chat.chatserver.common.protocol.ClientInitiatedResponse;
import karrot.chat.chatserver.domain.chat.entity.Message;
import karrot.chat.chatserver.domain.chat.service.MessageService;
import karrot.chat.chatserver.infra.redis.stream.RedisStreamProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SendMessageCommand implements ClientInitiatedCommand {

    private final ObjectMapper objectMapper;
    private final RedisStreamProducer redisStreamProducer;
    private final MessageService messageService;

    @Override
    public void execute(JsonNode payload, WebSocketSession session) throws IOException {
        SendMessageRequest sendMessageRequest = objectMapper.treeToValue(payload, SendMessageRequest.class);
        Result<Message> saveResult = messageService.save(
                sendMessageRequest.getChatId(),
                sendMessageRequest.getUserId(),
                sendMessageRequest.getMessage()
        );
        if (saveResult.isSuccess()) {
            Message message = saveResult.getData();
            redisStreamProducer.sendToStream("chat-stream", Map.of(
                    "userId", sendMessageRequest.getUserId().toString(),
                    "chatId", sendMessageRequest.getChatId().toString(),
                    "message", sendMessageRequest.getMessage(),
                    "createAt", message.getTime().toString()
            ));

            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(
                    ClientInitiatedResponse.success(getCommandType().name())
            )));
        } else {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(
                    ClientInitiatedResponse.fail(getCommandType().name(), saveResult.getErrorMessage())
            )));
        }
    }

    @Override
    public ClientInitiatedCommandType getCommandType() {
        return ClientInitiatedCommandType.SEND_MESSAGE;
    }


}
