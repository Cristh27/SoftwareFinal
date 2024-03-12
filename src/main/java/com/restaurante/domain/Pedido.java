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
@Entity
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int cantidad;
    private LocalDateTime fecha;
    private String estado;

    @JsonIgnore // Ignora la serialización de este campo en JSON
    @ManyToOne // Relación muchos a uno con la entidad Cliente
    @JoinColumn(name = "cliente_id") // Define la columna en la tabla de la base de datos para esta relación
    private Cliente cliente;

    @JsonIgnore // Ignora la serialización de este campo en JSON
    @ManyToMany // Relación muchos a muchos con la entidad Producto
    @JoinTable(
        name = "producto_pedido", // Nombre de la tabla intermedia
        joinColumns = @JoinColumn(name = "pedido_id"), // Columna que referencia a esta entidad en la tabla intermedia
        inverseJoinColumns = @JoinColumn(name = "producto_id") // Columna que referencia a la entidad Producto en la tabla intermedia
    )
    private List<Producto> productos;
}
