package com.restaurante.dto;

import java.util.List;

import com.restaurante.domain.Producto;

import lombok.Data;

/**
 * DTO que representa un producto en el sistema.
 */
@Data
public class ProductoDTO {
    /** 
     * Identificador único del producto. 
     */
    private Long id;
    
    /** 
     * Nombre del producto. 
     */
    private String nombre;
    
    /** 
     * Descripción del producto. 
     */
    private String descripcion;
    
    /** 
     * Precio del producto. 
     */
    private double precio;
    
    /** 
     * Producto variante asociado, si lo hay. 
     */
    private ProductoDTO variante;
    
    /** 
     * Lista de IDs de los pedidos asociados a este producto. 
     */
    private List<Producto> pedidosIds;
}
