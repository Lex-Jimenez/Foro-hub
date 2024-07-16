package alurachallenge.foro_hub.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException() {
        super();
    }
    public ResourceNotFoundException(String s) {
        super(s);
    }
}
