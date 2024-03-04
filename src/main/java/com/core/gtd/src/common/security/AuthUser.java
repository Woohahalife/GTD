package com.core.gtd.src.common.security;

import io.swagger.v3.oas.annotations.Parameter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // parameter를 적용해 전달 인자로 넘김
@Retention(RetentionPolicy.RUNTIME) // 정보 유지 시점은 컴파일 이후 런타임 시점에도 JVM에 의해 참조가 가능하도록 설정
@Parameter(hidden = true) // Swagger 문서에 포함 X
public @interface AuthUser {
}
