package com.restaurante.util;

import lombok.Data;

@Data
public class ApiResponse<T> {
	/**
     * Indica si la operación fue exitosa.
     */
    private boolean success;
    
    /**
     * Mensaje descriptivo sobre el resultado de la operación.
     */
    private String message;
    
    /**
     * Los datos asociados a la respuesta.
     */
    private T data;

    /**
     * Constructor de la clase ApiResponse.
     *
     * @param success indica si la operación fue exitosa
     * @param message mensaje descriptivo sobre el resultado de la operación
     * @param data los datos asociados a la respuesta
     */
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
