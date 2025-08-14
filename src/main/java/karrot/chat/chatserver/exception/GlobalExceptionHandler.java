package karrot.chat.chatserver.exception;

import karrot.chat.chatserver.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleGenericException(Exception e) {
        log.error("api error", e);
        return ApiResponse.internalServerError("서버에 오류가 발생했습니다. 잠시 후 다시 시도해주세요");
    }
}
