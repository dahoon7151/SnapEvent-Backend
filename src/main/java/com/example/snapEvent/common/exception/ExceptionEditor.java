package com.example.snapEvent.common.exception;

import com.example.snapEvent.member.jwt.TokenNotValidateException;
import jakarta.persistence.ElementCollection;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.amqp.RabbitStreamTemplateConfigurer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionEditor {

    // 컨트롤러 form validation 핸들러
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MethodInvalidResponse> methodInvalidException(
            final MethodArgumentNotValidException e
    ) {
        BindingResult bindingResult = e.getBindingResult();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(MethodInvalidResponse.builder()
                        .error(bindingResult.getFieldErrors().get(0).getCode())
                        .message(bindingResult.getFieldErrors().get(0).getDefaultMessage())
                        .build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(
            final IllegalArgumentException e
    ) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    // 서비스 단에서 사용되는 validationToken 메소드 때문에 추가
    @ExceptionHandler(TokenNotValidateException.class)
    public ResponseEntity<String> handleTokenException(
            final TokenNotValidateException e
    ) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

//    @ExceptionHandler(UsernameNotFoundException.class)
//    public ResponseEntity<String> handleNoUsernameException(
//            final UsernameNotFoundException e
//    ) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//    }

    @Getter
    @Builder
    public static class MethodInvalidResponse {

        private String error;
        private String message;
    }
}