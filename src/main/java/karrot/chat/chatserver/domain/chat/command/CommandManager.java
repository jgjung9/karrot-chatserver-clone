package karrot.chat.chatserver.domain.chat.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import karrot.chat.chatserver.domain.chat.dto.protocol.ClientToServerRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class CommandManager {

    private final ObjectMapper objectMapper;
    private final Map<Commands, Command> commands = new ConcurrentHashMap<>();
    private final Command defaultCommand = new DefaultCommand();

    public CommandManager(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

        for (Commands command : Commands.values()) {
            switch (command) {
                case SEND_MESSAGE -> commands.put(command, new SendMessageCommand(objectMapper));
            }
        }
    }

    public void execute(WebSocketSession session, TextMessage message) {
        try {
            ClientToServerRequest request = objectMapper.readValue(message.getPayload(), ClientToServerRequest.class);
            Command command = commands.get(Commands.valueOf(request.getCommand()));
            command.execute(request.getBody(), session);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(WebSocketSession session) {

    }
}
