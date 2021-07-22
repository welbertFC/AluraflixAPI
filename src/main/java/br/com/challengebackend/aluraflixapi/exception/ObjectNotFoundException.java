package br.com.challengebackend.aluraflixapi.exception;

public class ObjectNotFoundException extends RuntimeException{

    private static final String NOTFOUND = "Object not found";

    public ObjectNotFoundException() {
        super(NOTFOUND);
    }
}
