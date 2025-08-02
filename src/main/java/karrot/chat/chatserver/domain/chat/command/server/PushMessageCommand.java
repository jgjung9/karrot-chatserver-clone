package karrot.chat.chatserver.domain.chat.command.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import karrot.chat.chatserver.domain.chat.dto.protocol.ServerToClientRequest;
import karrot.chat.chatserver.domain.chat.session.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class PushMessageCommand implements ServerCommand {

    private final SessionManager sessionManager;
    private final ObjectMapper objectMapper;

    @Override
    public void execute(Long userId, Object body) throws IOException {
        WebSocketSession session = sessionManager.getSession(userId);
        ServerToClientRequest request = new ServerToClientRequest();
        request.setCommand(getCommandType().name());
        request.setBody(objectMapper.writeValueAsString(body));
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(request)));
    }

    @Override
    public ServerCommands getCommandType() {
        return ServerCommands.PUSH_MESSAGE;
    }
}
