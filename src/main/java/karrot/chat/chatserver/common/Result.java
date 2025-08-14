package karrot.chat.chatserver.common;

import karrot.chat.chatserver.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 서비스 계층에서 발생한 결과를 처리
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Result<T> {
    private final boolean success;
    private final T data;
    private final String errorCode;
    private final String errorMessage;

    // 성공 응답 생성
    public static <T> Result<T> success(T data) {
        return new Result<>(true, data, null, null);
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    // 실패 응답 생성
    public static <T> Result<T> failure(String errorCode, String errorMessage) {
        return new Result<>(false, null, errorCode, errorMessage);
    }

    public static <T> Result<T> failure(ErrorCode errorCode) {
        return failure(errorCode.getCode(), errorCode.getMessage());
    }

    public boolean isFailure() {
        return !success;
    }
}
