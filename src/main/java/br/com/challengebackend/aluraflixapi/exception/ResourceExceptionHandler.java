package br.com.challengebackend.aluraflixapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError<Map<String, String>>> validation(
            MethodArgumentNotValidException e, HttpServletRequest request) {
        var erros = new HashMap<String, String>();

        e.getFieldErrors().forEach(fieldError -> erros.put(fieldError.getField(),
                fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : ""));

        return ResponseEntity.badRequest().body(new StandardError<>(
                HttpStatus.BAD_REQUEST.value(),
                erros,
                "Erro de validação",
                Calendar.getInstance().getTimeInMillis(),
                request.getRequestURI()));
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(
            ObjectNotFoundException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StandardError(
                HttpStatus.NOT_FOUND.value(),
                "Object not found",
                e.getMessage(),
                System.currentTimeMillis(),
                request.getRequestURI()));
    }
}
