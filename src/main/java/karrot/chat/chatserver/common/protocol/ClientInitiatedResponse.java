package karrot.chat.chatserver.common.protocol;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientInitiatedResponse<T> {
    private final String command;
    private final boolean success;
    private final T payload;
    private final String error;
    private final LocalDateTime timestamp;

    public static <T> ClientInitiatedResponse<T> success(String command) {
        return success(command, null);
    }

    public static <T> ClientInitiatedResponse<T> success(String command, T data) {
        return new ClientInitiatedResponse<>(command, true, data, null, LocalDateTime.now());
    }

    public static <T> ClientInitiatedResponse<T> fail(String command, String error) {
        return new ClientInitiatedResponse<>(command, false, null, error, LocalDateTime.now());
    }
}
