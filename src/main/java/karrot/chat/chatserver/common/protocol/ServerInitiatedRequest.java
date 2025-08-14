package karrot.chat.chatserver.common.protocol;

import lombok.*;

/**
 * server -> client -> server
 */
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ServerInitiatedRequest<T> {
    private final String command;
    private final T payload;
}
