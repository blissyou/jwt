package org.example.auth.JavaWebToken.exception;


public class NotFoundMemberException extends RuntimeException {
    public NotFoundMemberException() {
        super();
    }
    public NotFoundMemberException(String message, Throwable cause){
        super(message, cause);
    }

    public NotFoundMemberException(String message) {
        super(message);
    }

}
