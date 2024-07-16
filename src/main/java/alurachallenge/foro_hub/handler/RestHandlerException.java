package alurachallenge.foro_hub.handler;

import alurachallenge.foro_hub.exceptions.BadRequestException;
import alurachallenge.foro_hub.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@RestControllerAdvice
public class RestHandlerException {

    @Autowired
    private MessageSource messagesSource;

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail handlerValidationError(MethodArgumentNotValidException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        problemDetail.setTitle("Error de argumento: metodo no valido");
        problemDetail.setType(URI.create("https://Api.com/error/Argument_not_valid"));
        problemDetail.setDetail("Error: no es posible realizar la consulta ya que tiene errores");

        Set<String> errors = new HashSet<>();
        List<FieldError> fieldErrors = ex.getFieldErrors();

        for (FieldError fiel:fieldErrors){
            String messages = messagesSource.getMessage(fiel, Locale.getDefault());
            errors.add(messages);
        }
        problemDetail.setProperty("errors", errors);
        return problemDetail;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    ProblemDetail handlerResourceNotFoundException(ResourceNotFoundException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Solicitud no encontrada");
        problemDetail.setType(URI.create("https://Api.com/error/not_found"));
        return problemDetail;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    ProblemDetail handlerBadRequestException(BadRequestException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Solicitud incorrecta");
        problemDetail.setType(URI.create("https://Api.com/error/bad_request"));
        return problemDetail;
    }
}
