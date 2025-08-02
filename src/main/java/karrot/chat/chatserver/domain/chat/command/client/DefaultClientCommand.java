package karrot.chat.chatserver.domain.chat.command.client;

import com.fasterxml.jackson.databind.JsonNode;
import karrot.chat.chatserver.domain.chat.command.client.ClientCommand;
import org.springframework.web.socket.WebSocketSession;

public class DefaultClientCommand implements ClientCommand {
    @Override
    public void execute(JsonNode payload, WebSocketSession session) {
        // TODO: 클라에서 온 요청이 없는 command 일 경우 예외 처리
    }

    @Override
    public ClientCommands getCommandType() {
        return null;
    }
}
