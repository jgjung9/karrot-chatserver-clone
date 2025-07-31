package karrot.chat.chatserver.domain.chat.command;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.socket.WebSocketSession;

public class DefaultClientCommand implements ClientCommand {
    @Override
    public void execute(JsonNode payload, WebSocketSession session) {

    }
}
