package karrot.chat.chatserver.common.protocol;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ServerInitiatedRequest<T> {
    private final String command;
    private final T payload;
}
