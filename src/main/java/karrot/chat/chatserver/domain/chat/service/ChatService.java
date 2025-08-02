package karrot.chat.chatserver.domain.chat.service;

import karrot.chat.chatserver.domain.chat.command.CommandManager;
import karrot.chat.chatserver.domain.chat.command.server.ServerCommands;
import karrot.chat.chatserver.domain.chat.dto.payload.PushMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final CommandManager commandManager;

    public void sendChat(PushMessageRequest request) {
        commandManager.send(request.getUserId(), ServerCommands.PUSH_MESSAGE, request);
    }
}
