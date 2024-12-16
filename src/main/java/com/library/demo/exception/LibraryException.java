package com.library.demo.exception;

public class LibraryException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Crea una LibraryException sin mensaje detallado.
     */
    public LibraryException() {
        super();
    }

    /**
     * Crea una LibraryException con un mensaje detallado.
     * @param message el mensaje de error
     */
    public LibraryException(String message) {
        super(message);
    }

    /**
     * Crea una LibraryException con un mensaje detallado y una causa.
     * @param message el mensaje de error
     * @param cause la causa original de la excepción
     */
    public LibraryException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Crea una LibraryException con una causa.
     * @param cause la causa original de la excepción
     */
    public LibraryException(Throwable cause) {
        super(cause);
    }
}
