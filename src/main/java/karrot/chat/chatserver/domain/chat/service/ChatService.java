package karrot.chat.chatserver.domain.chat.service;

import karrot.chat.chatserver.domain.chat.command.CommandManager;
import karrot.chat.chatserver.domain.chat.command.server.ServerInitiatedCommandType;
import karrot.chat.chatserver.domain.chat.dto.payload.DeliverMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {

    private final CommandManager commandManager;

    public void sendMessageToClient(DeliverMessageRequest request) {
        commandManager.executeServerInitiated(request.getReceiverId(), ServerInitiatedCommandType.DELIVER_MESSAGE, request);
    }
}
