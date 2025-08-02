package karrot.chat.chatserver.domain.chat.controller;

import karrot.chat.chatserver.domain.chat.dto.payload.PushMessageRequest;
import karrot.chat.chatserver.domain.chat.service.ChatService;
import karrot.chat.chatserver.infra.redis.stream.RedisStreamProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @PutMapping("send")
    public String sendMessage(@RequestBody PushMessageRequest request) {
        chatService.sendChat(request);
        return "성공";
    }
}
