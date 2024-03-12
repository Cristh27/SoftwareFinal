package com.restaurante.exception;

/**
 * Excepción que indica una operación ilegal o no permitida.
 */
public class IllegalOperationException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor que crea una nueva instancia de IllegalOperationException con el mensaje de error especificado.
     * @param message El mensaje de error.
     */
    public IllegalOperationException(String message) {
        super(message);
    }
}
