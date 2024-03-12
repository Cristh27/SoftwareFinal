package com.restaurante.exception;

import lombok.Data;

/**
 * Clase que representa una respuesta de error enviada por el servidor.
 */
@Data
public class ErrorResponse {
    private String message;

    /**
     * Constructor que crea una nueva instancia de ErrorResponse con el mensaje de error especificado.
     * @param message El mensaje de error.
     */
    public ErrorResponse(String message) {
        this.message = message;
    }
}
