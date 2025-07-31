package karrot.chat.chatserver.domain.chat.dto.protocol;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class ClientToServerRequest {
    private String command;
    private JsonNode body;
}
