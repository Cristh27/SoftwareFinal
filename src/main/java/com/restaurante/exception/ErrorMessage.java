package com.restaurante.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Data;

/**
 * Clase que contiene mensajes de error constantes para diferentes situaciones en la aplicación.
 */
@Data
public final class ErrorMessage {
	
	private int statusCode;
    
    /** Fecha y hora en que se generó el mensaje de error. */
    private LocalDateTime timestamp;
    
    /** Mensaje de error. */
    private String mensaje;
    
    /** Descripción detallada del error. */
    private String descripcion;

    /**
    * Constructor para inicializar un objeto ErrorMessage.
     *
     * @param statusCode  Código de estado HTTP asociado con el mensaje de error.
     * @param mensaje     Mensaje de error.
     * @param descripcion Descripción detallada del error.
     */
    public ErrorMessage(HttpStatus statusCode, String mensaje, String descripcion) {
        this.statusCode = statusCode.value();
        this.timestamp = LocalDateTime.now();
        this.mensaje = mensaje;
        this.descripcion = descripcion;
    }
    // Mensajes de error para la entidad Cliente
    public static final String CLIENTE_NOT_FOUND = "El cliente con el ID proporcionado no fue encontrado";
    public static final String CLIENTE_NOT_DELETE = "El cliente no puede ser eliminado porque tiene pedidos asociados";

    // Mensajes de error para la entidad Pedido
    public static final String PEDIDO_NOT_DELETE = "El pedido no puede ser eliminado porque tiene clientes asociados";
    public static final String PEDIDO_NOT_FOUND = "El pedido no puede ser eliminado porque tiene clientes asociados";
    
    
    // Mensajes de error para la entidad Producto
    public static final String PRODUCTO_NOT_DELETE = "El producto no puede ser eliminado porque está asociado a pedidos";
    public static final String PRODUCT_NOT_FOUND = "El producto no ha podido ser encontrado";
    
    
    // Mensajes de error para la entidad Perfil
    public static final String PERFIL_NOT_FOUND = "El perfil con el ID proporcionado no fue encontrado";
    
 

    // Constructor privado para evitar la instanciación de la clase
    private ErrorMessage() {
        throw new IllegalStateException("Clase de utilidad");
    }
}
