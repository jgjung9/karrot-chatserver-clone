package karrot.chat.chatserver.common.protocol;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientInitiatedRequest {
    private String command;
    private JsonNode payload;
}
