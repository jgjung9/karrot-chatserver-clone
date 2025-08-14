package karrot.chat.chatserver.exception;

import lombok.Getter;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Getter
public enum ErrorCode {
    CHAT_NOT_FOUND("C0001", "채팅방이 없습니다."),
    CHAT_NOT_CONTAINS_USER("C0002", "접속해 있는 채팅방에만 메시지를 전송할 수 있습니다");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorCode ofCode(String code) {
        return Arrays.stream(ErrorCode.values())
                .filter(errorCode -> errorCode.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("ErrorCode Not Found: " + code));
    }
}
