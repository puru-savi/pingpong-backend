package com.un.pingpong.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {PingPongException.class, Exception.class})
    public ResponseEntity<PingPongException> pingPongException(PingPongException ex, WebRequest request) {
        return ResponseEntity.status(ex.code).body(new PingPongException(ex.getCode(),ex.getMessage()));
    }
}
