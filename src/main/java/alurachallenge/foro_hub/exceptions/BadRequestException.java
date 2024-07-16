package alurachallenge.foro_hub.exceptions;

import org.springframework.dao.DataAccessException;

import java.io.IOException;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String s) {
        super(s);
    }
    public BadRequestException(String s, DataAccessException e) {
        super(s,e);
    }

    public BadRequestException(String s, IOException e) {
        super(s,e);
    }
}
