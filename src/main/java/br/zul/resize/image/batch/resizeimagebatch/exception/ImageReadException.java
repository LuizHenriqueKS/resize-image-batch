package br.zul.resize.image.batch.resizeimagebatch.exception;

public class ImageReadException extends RuntimeException {
    
    public ImageReadException(Throwable cause, String message) {
        super(message, cause);
    }

}
