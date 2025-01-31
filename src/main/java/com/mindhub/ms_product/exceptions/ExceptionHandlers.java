package com.mindhub.ms_product.exceptions;

import jakarta.ws.rs.NotAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(NotValidArgumentException.class)
    public ResponseEntity<String> handleNotValidArgumentException(NotValidArgumentException notValidArgument) {
        return new ResponseEntity<>(notValidArgument.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundExceptionException(NotFoundException notFoundException) {
        return new ResponseEntity<>(notFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(jakarta.ws.rs.NotAuthorizedException.class)
    public ResponseEntity<String> handleNotAuthorizedException(NotAuthorizedException notAuthorizedException) {
        return new ResponseEntity<>(notAuthorizedException.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
