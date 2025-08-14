package karrot.chat.chatserver.domain.chat.command.server;

import java.io.IOException;

public interface ServerInitiatedCommand {
    void execute(Long userId, Object payload) throws IOException;

    ServerInitiatedCommandType getCommandType();
}
