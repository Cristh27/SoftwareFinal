package com.restaurante.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

/**
 * Entidad que representa un producto en el sistema.
 */
@Entity
@Data
public class Producto {
    /** 
     * Representa el identificador único para el producto. 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** 
     * Representa el nombre del producto. 
     * Limitado a un máximo de 100 caracteres.
     */
    private String nombre;
    
    /** 
     * Representa la descripción del producto. 
     * Puede incluir información sobre ingredientes, tamaño, peso, etc.
     * Limitado a un máximo de 100 caracteres.
     */
    private String descripcion;
    
    /** 
     * Representa el precio del producto. 
     */
    private double precio;

    /** 
     * Relación muchos a uno con la entidad Producto. 
     * Indica la variante de este producto, si es aplicable.
     */
    @ManyToOne // Define una relación muchos a uno con la entidad Producto, representando la relación entre un producto y su variante
    @JoinColumn(name = "variante_id") // Especifica la columna en la tabla de la base de datos que almacena la relación con la variante del producto
    private Producto variante; // Producto que actúa como variante de este producto

    /** 
     * Relación muchos a muchos con la entidad Pedido. 
     * Indica los pedidos en los que este producto ha sido incluido.
     */
    @ManyToMany(mappedBy = "productos") // Define una relación muchos a muchos con la entidad Pedido, representando la relación entre los productos y los pedidos
    private List<Pedido> pedidos; // Pedidos asociados a este producto
}
