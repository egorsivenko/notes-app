package org.example.todolist.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.example.todolist.exception.InsufficientPrivilegesException;
import org.example.todolist.exception.NoteNotFoundException;
import org.example.todolist.exception.UsernameTakenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<ApiError> handleNoteNotFoundException(
            NoteNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiError apiError = buildApiError(request, e, status);
        return new ResponseEntity<>(apiError, status);
    }

    @ExceptionHandler(UsernameTakenException.class)
    public ResponseEntity<ApiError> handleUsernameTakenException(
            UsernameTakenException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiError apiError = buildApiError(request, e, status);
        return new ResponseEntity<>(apiError, status);
    }

    @ExceptionHandler(InsufficientPrivilegesException.class)
    public ResponseEntity<ApiError> handleInsufficientPrivilegesException(
            InsufficientPrivilegesException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ApiError apiError = buildApiError(request, e, status);
        return new ResponseEntity<>(apiError, status);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(
            AuthenticationException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ApiError apiError = buildApiError(request, e, status);
        return new ResponseEntity<>(apiError, status);
    }

    private ApiError buildApiError(HttpServletRequest request, Exception e, HttpStatus status) {
        return new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                status.value(),
                LocalDateTime.now()
        );
    }
}
