package karrot.chat.chatserver.domain.chat.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import karrot.chat.chatserver.domain.chat.command.client.ClientInitiatedCommand;
import karrot.chat.chatserver.domain.chat.command.client.ClientInitiatedCommandType;
import karrot.chat.chatserver.domain.chat.command.server.ServerInitiatedCommand;
import karrot.chat.chatserver.domain.chat.command.server.ServerInitiatedCommandType;
import karrot.chat.chatserver.common.protocol.ClientInitiatedRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class CommandManager {

    private final ObjectMapper objectMapper;
    private final Map<ClientInitiatedCommandType, ClientInitiatedCommand> clientCommands = new ConcurrentHashMap<>();
    private final Map<ServerInitiatedCommandType, ServerInitiatedCommand> serverCommands = new ConcurrentHashMap<>();
    private final ClientInitiatedCommand defaultClientInitiatedCommand;

    public CommandManager(
            ObjectMapper objectMapper,
            List<ClientInitiatedCommand> clientInitiatedCommandList,
            List<ServerInitiatedCommand> serverInitiatedCommandList,
            ClientInitiatedCommand defaultClientInitiatedCommand
    ) {
        this.objectMapper = objectMapper;
        this.defaultClientInitiatedCommand = defaultClientInitiatedCommand;
        clientInitiatedCommandList.stream()
                .forEach(c -> {
                    if (c != defaultClientInitiatedCommand) {
                        clientCommands.put(c.getCommandType(), c);
                    }
                });
        serverInitiatedCommandList.stream()
                .forEach(c -> serverCommands.put(c.getCommandType(), c));
    }

    public void executeClientInitiated(WebSocketSession session, TextMessage message) {
        try {
            ClientInitiatedRequest request = objectMapper.readValue(message.getPayload(), ClientInitiatedRequest.class);
            ClientInitiatedCommand command;
            try {
                command = clientCommands.get(ClientInitiatedCommandType.valueOf(request.getCommand()));
            } catch (IllegalArgumentException e) {
                command = defaultClientInitiatedCommand;
            }
            command.execute(request.getPayload(), session);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void executeServerInitiated(Long userId, ServerInitiatedCommandType commandType, Object payload) {
        ServerInitiatedCommand command = serverCommands.get(commandType);

        try {
            command.execute(userId, payload);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
