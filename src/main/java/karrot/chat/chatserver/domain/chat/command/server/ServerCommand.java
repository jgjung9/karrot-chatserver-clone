package karrot.chat.chatserver.domain.chat.command.server;

public interface ServerCommand {
    void execute();

    ServerCommands getCommandType();
}
