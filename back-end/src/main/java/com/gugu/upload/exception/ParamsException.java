package com.gugu.upload.exception;

/**
 * The type Params exception.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
public class ParamsException extends RuntimeException{
    /**
     * Instantiates a new Params exception.
     *
     * @param message the message
     */
    public ParamsException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Params exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ParamsException(String message, Throwable cause) {
        super(message, cause);
    }
}
