package com.nexstudio.expensetracker_be.exception;

import com.nexstudio.expensetracker_be.constants.ErrorConstants;
import com.nexstudio.expensetracker_be.dto.response.api.ErrorResponse;
import com.nexstudio.expensetracker_be.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException exception) {
        log.warn("Database error occurred", exception);

        return ResponseUtil.buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorConstants.DATABASE_ERROR,
                List.of(exception.getMostSpecificCause().getMessage())
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        log.warn("User not found", exception);

        return ResponseUtil.buildErrorResponse(
                HttpStatus.NOT_FOUND,
                ErrorConstants.AUTHENTICATION_ERROR,
                List.of(exception.getMessage())
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException exception) {
        log.warn("Invalid username or password", exception);

        return ResponseUtil.buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                ErrorConstants.AUTHENTICATION_ERROR,
                List.of(exception.getMessage())
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
        log.warn("Invalid request", exception);

        return ResponseUtil.buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                ErrorConstants.AUTHENTICATION_ERROR,
                List.of(exception.getMessage())
        );
    }

    @ExceptionHandler(UsernameValidationException.class)
    public ResponseEntity<ErrorResponse> handleUsernameValidationException(UsernameValidationException exception) {
        log.warn("Username validation failed", exception);

        return ResponseUtil.buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                ErrorConstants.USERNAME_VALIDATION_ERROR,
                List.of(exception.getMessage())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.warn("Validation failed", exception);

        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .toList();

        return ResponseUtil.buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                errors
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        log.warn("Unexpected error occurred", exception);

        return ResponseUtil.buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorConstants.GENERAL_ERROR,
                List.of(exception.getMessage())
        );
    }
}
