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
    /** 
     * Representa el identificador único para el perfil. 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** 
     * Representa las preferencias del cliente. 
     * Puede contener información sobre preferencias de comida, bebida, servicios, etc.
     * Limitado a un máximo de 100 caracteres.
     */
    private String preferencias;

    /** 
     * Relación uno a uno con la entidad Cliente. 
     * Indica el cliente asociado a este perfil.
     */
    @OneToOne // Define una relación uno a uno con la entidad Cliente
    @JoinColumn(name = "cliente_id") // Especifica la columna en la tabla de la base de datos que almacena la relación con la entidad Cliente
    private Cliente cliente; // Cliente asociado a este perfil
}

