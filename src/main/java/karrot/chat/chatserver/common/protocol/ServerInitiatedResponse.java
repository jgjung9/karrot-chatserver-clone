package karrot.chat.chatserver.common.protocol;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * server -> client -> server
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerInitiatedResponse {
    private String command;
    private boolean success;
    private JsonNode payload;
    private String error;
    private LocalDateTime timestamp;
}
