package com.core.gtd.src.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ResponseStatus {

    /**
     * 200(OK)
     */
    SUCCESS(true, OK.value(), "요청에 성공했습니다."),
    EMPTY_TASKS(true, OK.value(), "게시물이 존재하지 않습니다"),

    /**
     * 400(Bad_request)
     */
    TASK_DOES_NOT_EXIST(false, BAD_REQUEST.value(), "해당 게시물이 존재하지 않습니다."),
    DEADLINE_IS_INCORRECT(false, BAD_REQUEST.value(), "마감 기한이 잘못 설정되었습니다."),
    DELETE_IS_FAIL(false, BAD_REQUEST.value(), "삭제가 완료되지 않았습니다."),
    USER_NOT_FOUND(false, BAD_REQUEST.value(), "유저 정보를 찾을 수 업습니다."),

    NOT_BEARER_TOKEN(false, BAD_REQUEST.value(), "토큰이 BEARER로 시작하지 않거나 null입니다."),
    INVALID_PASSWORD(false, BAD_REQUEST.value(), "비밀번호가 일지하지 않습니다."),
    DUPLICATE_USERNAME(false, BAD_REQUEST.value(), "같은 아이디가 이미 존재합니다."),
    DUPLICATE_EMAIL(false, BAD_REQUEST.value(), "같은 이메일이 이미 존재합니다."),

    PRINCIPAL_IS_NOTFOUND(false, BAD_REQUEST.value(), "사용자 인증 주체 정보가 존재하지 않습니다."),
    NOT_AUTHENTICATED_USER(false, BAD_REQUEST.value(), "인증된 사용자가 아닙니다."),
    /**
     * 500(Internal_Server_Error)
     */
    INVALID_ERROR(false, INTERNAL_SERVER_ERROR.value(), "예상치 못한 에러가 발생했습니다.");

    private final boolean isSuccess;
    private final int statusCode;
    private final String message;
}

