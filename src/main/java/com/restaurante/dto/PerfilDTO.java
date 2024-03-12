package com.restaurante.dto;

import lombok.Data;

/**
 * DTO que representa el perfil de un cliente en el sistema.
 */
@Data
public class PerfilDTO {
    /** 
     * Identificador único del perfil. 
     */
    private Long id;
    
    /** 
     * Preferencias del perfil del cliente. 
     */
    private String preferencias;
    
    /** 
     * Identificador del cliente asociado al perfil. 
     */
    private Long clienteId;
}
