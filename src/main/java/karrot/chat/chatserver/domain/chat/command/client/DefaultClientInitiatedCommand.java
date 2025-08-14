package karrot.chat.chatserver.domain.chat.command.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import karrot.chat.chatserver.common.protocol.ClientInitiatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class DefaultClientInitiatedCommand implements ClientInitiatedCommand {

    private final ObjectMapper objectMapper;

    @Override
    public void execute(JsonNode payload, WebSocketSession session) throws IOException {
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(
                ClientInitiatedResponse.fail("INVALID_COMMAND", "없는 요청입니다."))));
    }

    @Override
    public ClientInitiatedCommandType getCommandType() {
        return null;
    }
}
