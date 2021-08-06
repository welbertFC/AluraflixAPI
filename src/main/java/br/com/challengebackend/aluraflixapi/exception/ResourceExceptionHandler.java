package br.com.challengebackend.aluraflixapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError<Map<String, String>>> validation(
            MethodArgumentNotValidException e, HttpServletRequest request) {
        var errors = new HashMap<String, String>();

        e.getFieldErrors().forEach(fieldError -> errors.put(fieldError.getField(),
                fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : ""));

        return ResponseEntity.badRequest().body(new StandardError<>(
                HttpStatus.BAD_REQUEST.value(),
                errors,
                "Erro de validação",
                Calendar.getInstance().getTimeInMillis(),
                request.getRequestURI()));
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError<String>> objectNotFound(
            ObjectNotFoundException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StandardError<>(
                HttpStatus.NOT_FOUND.value(),
                "Object not found",
                e.getMessage(),
                System.currentTimeMillis(),
                request.getRequestURI()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<StandardError<String>> accessDenied(
            AccessDeniedException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StandardError<>(
                HttpStatus.NOT_FOUND.value(),
                "Access Denied",
                e.getMessage(),
                System.currentTimeMillis(),
                request.getRequestURI()));
    }

    @ExceptionHandler(ArgumentNotValidException.class)
    public ResponseEntity<StandardError<String>> argumentInvalid(
            ArgumentNotValidException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new StandardError<>(
                HttpStatus.CONFLICT.value(),
                "Argument Not Valid",
                e.getMessage(),
                System.currentTimeMillis(),
                request.getRequestURI()));
    }

    @ExceptionHandler(ObjectAlreadyCreatedException.class)
    public ResponseEntity<StandardError<String>> alreadyCreated(
            ObjectAlreadyCreatedException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StandardError<>(
                HttpStatus.BAD_REQUEST.value(),
                "Already Created",
                e.getMessage(),
                System.currentTimeMillis(),
                request.getRequestURI()));
    }
}
