package karrot.chat.chatserver.domain.chat.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import karrot.chat.chatserver.domain.chat.command.client.ClientCommand;
import karrot.chat.chatserver.domain.chat.command.client.ClientCommands;
import karrot.chat.chatserver.domain.chat.command.client.DefaultClientCommand;
import karrot.chat.chatserver.domain.chat.command.server.ServerCommand;
import karrot.chat.chatserver.domain.chat.command.server.ServerCommands;
import karrot.chat.chatserver.domain.chat.dto.protocol.ClientToServerRequest;
import karrot.chat.chatserver.domain.chat.dto.protocol.ServerToClientRequest;
import karrot.chat.chatserver.domain.chat.session.SessionManager;
import karrot.chat.chatserver.infra.redis.stream.RedisStreamProducer;
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
    private final SessionManager sessionManager;
    private final RedisStreamProducer redisStreamProducer;
    private final Map<ClientCommands, ClientCommand> clientCommands = new ConcurrentHashMap<>();
    private final Map<ServerCommands, ServerCommand> serverCommands = new ConcurrentHashMap<>();
    private final ClientCommand defaultClientCommand = new DefaultClientCommand();

    public CommandManager(
            ObjectMapper objectMapper,
            SessionManager sessionManager,
            RedisStreamProducer redisStreamProducer,
            List<ClientCommand> clientCommandList,
            List<ServerCommand> serverCommandList
    ) {
        this.objectMapper = objectMapper;
        this.sessionManager = sessionManager;
        this.redisStreamProducer = redisStreamProducer;

        clientCommandList.stream()
                .forEach(c -> clientCommands.put(c.getCommandType(), c));
        serverCommandList.stream()
                .forEach(c -> serverCommands.put(c.getCommandType(), c));
    }

    public void execute(WebSocketSession session, TextMessage message) {
        try {
            ClientToServerRequest request = objectMapper.readValue(message.getPayload(), ClientToServerRequest.class);
            ClientCommand command;
            try {
                command = clientCommands.get(ClientCommands.valueOf(request.getCommand()));
            } catch (IllegalArgumentException e) {
                command = defaultClientCommand;
            }
            command.execute(request.getBody(), session);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(Long userId, ServerCommands command, Object body){
        WebSocketSession session = sessionManager.getSession(userId);
        if (session == null || !session.isOpen()) {
            // TODO: 예외 처리
            return;
        }

        try {
            ServerToClientRequest request = new ServerToClientRequest();
            request.setCommand(command.name());
            request.setBody(objectMapper.writeValueAsString(body));
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(request)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
