package karrot.chat.chatserver.domain.chat.command;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import karrot.chat.chatserver.domain.chat.dto.payload.SendMessageRequest;
import karrot.chat.chatserver.domain.chat.dto.protocol.ClientToServerResponse;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.LocalDateTime;

public class SendMessageCommand implements ClientCommand {

    private final ObjectMapper objectMapper;

    public SendMessageCommand(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void execute(JsonNode payload, WebSocketSession session) throws IOException {
        SendMessageRequest sendMessageRequest = objectMapper.treeToValue(payload, SendMessageRequest.class);

        // TODO: redis message queue에 등록

        ClientToServerResponse response = new ClientToServerResponse();
        response.setCommand("SEND_MESSAGE");
        response.setSuccess(true);
        response.setMessage("성공");
        response.setSentAt(LocalDateTime.now());
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(response)));
    }
}
