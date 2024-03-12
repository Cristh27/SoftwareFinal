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
 * Clase que representa un cliente en la persistencia.
 */
/**
 * Clase que representa un cliente en la persistencia.
 */
@Entity
@Data
public class Cliente {
    /** 
     * Representa el identificador único para la entidad Cliente. 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** 
     * Representa el nombre del cliente. 
     * Limitado a un máximo de 100 caracteres.
     */
    
    private String nombre;
    
    /** 
     * Representa el correo electrónico del cliente. 
     * Limitado a un máximo de 100 caracteres.
     */
    
    private String correoElectronico;
    
    /** 
     * Representa el número telefónico del cliente. 
     * Limitado a un máximo de 20 caracteres.
     */
  
    private String numeroTelefonico;

    /** 
     * Relación OneToOne con la entidad Perfil. 
     * Indica el perfil asociado al cliente. 
     */
    @OneToOne(mappedBy = "cliente")
    @JsonIgnoreProperties("cliente") // Ignora la serialización de la propiedad cliente en Perfil
    private Perfil perfil;

    /** 
     * Relación OneToMany con la entidad Pedido. 
     * Indica la lista de pedidos realizados por el cliente. 
     */
    @OneToMany(mappedBy = "cliente")
    @JsonIgnore // Esta anotación evita la serialización de la lista de pedidos en JSON
    private List<Pedido> pedidos;
}


