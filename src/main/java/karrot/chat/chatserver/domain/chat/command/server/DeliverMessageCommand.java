package karrot.chat.chatserver.domain.chat.command.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import karrot.chat.chatserver.domain.chat.dto.payload.DeliverMessageRequest;
import karrot.chat.chatserver.common.protocol.ServerInitiatedRequest;
import karrot.chat.chatserver.domain.chat.session.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class DeliverMessageCommand implements ServerInitiatedCommand {

    private final SessionManager sessionManager;
    private final ObjectMapper objectMapper;

    @Override
    public void execute(Long userId, Object payload) throws IOException {
        if (payload instanceof DeliverMessageRequest request) {
            WebSocketSession session = sessionManager.getSession(userId);
            if (session == null || !session.isOpen()) {
                sessionManager.remove(userId);
                sendToPushServer(request);
                return;
            }

            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(
                    ServerInitiatedRequest.builder()
                    .command(getCommandType().name())
                    .payload(request)
                    .build())));

        } else {
            throw new IllegalArgumentException("PushMessageCommand: Required PushMessageRequest payload");
        }
    }

    @Override
    public ServerInitiatedCommandType getCommandType() {
        return ServerInitiatedCommandType.DELIVER_MESSAGE;
    }

    private void sendToPushServer(DeliverMessageRequest request) {
        // TODO: 푸시 서버로 전달

    }
}
