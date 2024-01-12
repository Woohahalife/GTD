package com.core.gtd.common.exception;

import com.core.gtd.common.response.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppException extends RuntimeException {

    private ResponseStatus status;

    private String message;

    public AppException(ResponseStatus status) {
        this.status = status;
        this.message = null;
    }

    @Override
    public String getMessage() {
        if (message == null) {
            return status.getMessage();
        }
        return String.format("%s ,detail: %s", status.getMessage(), message);
    }
}