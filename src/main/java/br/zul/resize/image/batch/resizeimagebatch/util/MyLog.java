package br.zul.resize.image.batch.resizeimagebatch.util;

public class MyLog {
    
    public static void log(Object source, String message) {
        System.out.println(message);
    }

    public static void error(Object source, String message) {
        System.err.println(message);
    }

    public static void errorsf(Object source, String message, Object... args) {
        String formattedMessage = String.format(message, args);
        error(source, formattedMessage);
    }

    public static void logsf(Object source, String message, Object... args) {
        String formattedMessage = String.format(message, args);
        log(source, formattedMessage);
    }

}
