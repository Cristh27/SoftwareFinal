package com.restaurante.exception;

/**
 * Excepción personalizada que representa el caso en que una entidad no es encontrada.
 */
public class EntityNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor que acepta un mensaje para la excepción.
     *
     * @param message El mensaje de error asociado con la excepción.
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
