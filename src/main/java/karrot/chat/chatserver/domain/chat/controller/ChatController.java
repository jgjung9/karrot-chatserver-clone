package karrot.chat.chatserver.domain.chat.controller;

import karrot.chat.chatserver.common.ApiResponse;
import karrot.chat.chatserver.domain.chat.dto.payload.DeliverMessageRequest;
import karrot.chat.chatserver.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @PutMapping("deliver")
    @ResponseBody
    public ApiResponse<Void> deliverMessage(@RequestBody DeliverMessageRequest request) {
        chatService.sendMessageToClient(request);
        return ApiResponse.success();
    }
}
