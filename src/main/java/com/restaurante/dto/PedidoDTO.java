package com.restaurante.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.restaurante.domain.Cliente;
import com.restaurante.domain.Producto;

import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO que representa un pedido en el sistema.
 */
@Data
public class PedidoDTO {
    /** 
     * Identificador único del pedido. 
     */
    private Long id;
    
    /** 
     * Cantidad de productos en el pedido. 
     */
    private int cantidad;
    
    /** 
     * Fecha en la que se realizó el pedido. 
     */
    private LocalDateTime fecha;
    
    /** 
     * Estado actual del pedido. 
     */
    private String estado;
    
    /** 
     * Cliente que realizó el pedido. 
     */
    private Cliente cliente;
    
    /** 
     * Lista de productos incluidos en el pedido. 
     */
    private List<Producto> productos;
}
