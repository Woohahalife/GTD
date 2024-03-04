package com.core.gtd.src.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.core.gtd.src.common.response.ResponseStatus.SUCCESS;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "status", "data"})
public class BaseResponse<T> {

    private Boolean isSuccess;
    private int code;
    private String message;
    private ResponseStatus status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    // 성공 응답 구현
    public static <T> BaseResponse<T> response(T data) {
        return new BaseResponse<>(
                SUCCESS.isSuccess(),
                SUCCESS.getStatusCode(),
                SUCCESS.getMessage(),
                SUCCESS,
                data
        );
    }

    // 실패 응답 구현
    public static <T> BaseResponse<T> response(ResponseStatus status) {
        return new BaseResponse<>(
                status.isSuccess(),
                status.getStatusCode(),
                status.getMessage(),
                status,
                null
        );
    }
}
