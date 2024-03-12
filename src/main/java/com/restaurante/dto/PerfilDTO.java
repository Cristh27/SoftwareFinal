package com.restaurante.dto;

import lombok.Data;

@Data
public class PerfilDTO {
    private Long id;
    private String preferencias;
    private Long clienteId;
}