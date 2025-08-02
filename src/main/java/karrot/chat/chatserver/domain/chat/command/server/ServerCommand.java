package karrot.chat.chatserver.domain.chat.command.server;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface ServerCommand {
    void execute(Long userId, Object body) throws IOException;

    ServerCommands getCommandType();
}
