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

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardException<List<String>>> validation(
            MethodArgumentNotValidException e, HttpServletRequest request) {
        var erros = new HashMap<String, String>();

        e.getFieldErrors().forEach(fieldError -> erros.put(fieldError.getField(),
                fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : ""));

        StandardException<List<String>> error =
                new StandardException(
                        HttpStatus.BAD_REQUEST.value(),
                        erros,
                        "Erro de validação",
                        Calendar.getInstance().getTimeInMillis(),
                        request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardException> objectNotFound(
            ObjectNotFoundException e, HttpServletRequest request) {
        StandardException error =
                new StandardException(
                        HttpStatus.NOT_FOUND.value(),
                        "Object not found",
                        e.getMessage(),
                        System.currentTimeMillis(),
                        request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
