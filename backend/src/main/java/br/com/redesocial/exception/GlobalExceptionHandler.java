package br.com.redesocial.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {

        Throwable causa = e;

        while (causa.getCause() != null) {
            causa = causa.getCause();
        }

        String mensagem = causa.getMessage();

        try {
            mensagem = new String(mensagem.getBytes("ISO-8859-1"), "UTF-8");
        } catch (Exception ignored) {}

        return ResponseEntity
                .badRequest()
                .body(mensagem);
    }
}