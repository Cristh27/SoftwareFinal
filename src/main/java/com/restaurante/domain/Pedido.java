package com.restaurante.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

/**
 * Entidad que representa un pedido en el sistema.
 */
/**
 * Entidad que representa un pedido en el sistema.
 */
@Entity
@Data
public class Pedido {

    /** 
     * Representa el identificador único para el pedido. 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** 
     * Representa la cantidad del producto en el pedido. 
     */
    private int cantidad;
    
    /** 
     * Representa la fecha y hora en que se realizó el pedido. 
     */
    private LocalDateTime fecha;
    
    /** 
     * Representa el estado del pedido. 
     * Puede tener valores como "En proceso", "Completado", "Cancelado", etc.
     */
    private String estado;

    /** 
     * Relación muchos a uno con la entidad Cliente. 
     * Indica el cliente que realizó el pedido.
     */
    @JsonIgnore // Ignora la serialización de este campo en JSON
    @ManyToOne // Relación muchos a uno con la entidad Cliente
    @JoinColumn(name = "cliente_id") // Define la columna en la tabla de la base de datos para esta relación
    private Cliente cliente;

    /** 
     * Relación muchos a muchos con la entidad Producto. 
     * Indica los productos incluidos en el pedido.
     */
    @JsonIgnore // Ignora la serialización de este campo en JSON
    @ManyToMany // Relación muchos a muchos con la entidad Producto
    @JoinTable(
        name = "producto_pedido", // Nombre de la tabla intermedia
        joinColumns = @JoinColumn(name = "pedido_id"), // Columna que referencia a esta entidad en la tabla intermedia
        inverseJoinColumns = @JoinColumn(name = "producto_id") // Columna que referencia a la entidad Producto en la tabla intermedia
    )
    private List<Producto> productos;
}
