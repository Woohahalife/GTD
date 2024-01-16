package com.core.gtd.common.exception;

import com.core.gtd.common.response.BaseResponse;
import com.core.gtd.common.response.ResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<BaseResponse> appExceptionHandler(AppException e) {
        log.warn("[AppException Occurs] message: {} HttpStatusCode: {}", e.getStatus().getMessage(),
                e.getStatus().getStatusCode());

        return ResponseEntity.status(e.getStatus().getStatusCode())
                .body(BaseResponse.response(e.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> exceptionHandler(Exception e) {
        log.error("[InternalServerError Occurs] error: {}", e.getMessage());

        return ResponseEntity.internalServerError()
                .body(BaseResponse.response(ResponseStatus.INVALID_ERROR));
    }

}
