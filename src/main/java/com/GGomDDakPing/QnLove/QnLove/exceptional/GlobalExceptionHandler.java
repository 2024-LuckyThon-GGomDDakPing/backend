package com.GGomDDakPing.QnLove.QnLove.exceptional;

import com.GGomDDakPing.QnLove.QnLove.exceptional.Exceptionals;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exceptionals.class)
    public ResponseEntity<String> handleDuplicateResourceException(Exceptionals ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
