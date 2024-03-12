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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private double precio;

    @ManyToOne // Define una relación muchos a uno con la entidad Producto, representando la relación entre un producto y su variante
    @JoinColumn(name = "variante_id") // Especifica la columna en la tabla de la base de datos que almacena la relación con la variante del producto
    private Producto variante; // Producto que actúa como variante de este producto

    @ManyToMany(mappedBy = "productos") // Define una relación muchos a muchos con la entidad Pedido, representando la relación entre los productos y los pedidos
    private List<Pedido> pedidos; // Pedidos asociados a este producto

	
}
