package com.jamesfchen.vi;

public class JarException extends Exception {
    public JarException(Throwable cause) {
        super(cause);
    }

    public JarException(String message, Throwable cause) {
        super(message, cause);
    }

    public JarException(String message) {
        super(message);
    }

    public JarException() {
    }
}
