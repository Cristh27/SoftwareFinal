package com.restaurante.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

/**
 * Entidad que representa el perfil de un cliente en el sistema.
 */
@Entity
@Data
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String preferencias;

    @OneToOne // Define una relación uno a uno con la entidad Cliente
    @JoinColumn(name = "cliente_id") // Especifica la columna en la tabla de la base de datos que almacena la relación con la entidad Cliente
    private Cliente cliente; // Cliente asociado a este perfil
}
