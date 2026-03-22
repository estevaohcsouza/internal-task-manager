package br.com.estevaohcs.internaltaskmanager.controllers.handlers;

import br.com.estevaohcs.internaltaskmanager.services.exceptions.DataBaseException;
import br.com.estevaohcs.internaltaskmanager.services.exceptions.ResourceNotFoundException;
import br.com.estevaohcs.internaltaskmanager.services.exceptions.StatusDependencyException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Recurso não encontrado");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(StatusDependencyException.class)
    public ResponseEntity<StandardError> statusDependency(StatusDependencyException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Integridade de status violada");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<StandardError> entityNotFound(DataBaseException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Integridade da base de dados violada");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> valuesValidation(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ValidationError error = new ValidationError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Erro de validação");
        error.setPath(request.getRequestURI());
        for (FieldError f : e.getBindingResult().getFieldErrors()) {
            error.addError(f.getField(), f.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> handleMessageNotReadable(HttpMessageNotReadableException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Recurso não encontrado");
        error.setPath(request.getRequestURI());
        if (e.getMessage() != null && e.getMessage().contains("UUID")) {
            error.setMessage("ID (UUID) com formato inválido");
        }
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<StandardError> verifyParamValueType(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Parâmetro fornecido é inválido");
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

}
