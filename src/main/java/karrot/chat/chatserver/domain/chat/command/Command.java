package karrot.chat.chatserver.domain.chat.command;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public interface Command {

    void execute(JsonNode payload, WebSocketSession session) throws IOException;
}
