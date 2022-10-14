package br.zul.resize.image.batch.resizeimagebatch.exception;

public class InterruptedExecutationException extends RuntimeException {
    
    public InterruptedExecutationException(String message) {
        super(message);
    }

    public InterruptedExecutationException(Throwable cause) {
        super(cause);
    }

}
