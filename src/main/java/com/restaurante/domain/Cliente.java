package com.restaurante.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

/**
 * Entidad que representa un cliente en el sistema.
 */
@Entity
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String correoElectronico;
    private String numeroTelefonico;

    @OneToOne(mappedBy = "cliente")
    @JsonIgnoreProperties("cliente") // Ignora la serialización de la propiedad cliente en Perfil
    private Perfil perfil;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore // Esta anotación evita la serialización de la lista de pedidos en JSON
    private List<Pedido> pedidos;
}
