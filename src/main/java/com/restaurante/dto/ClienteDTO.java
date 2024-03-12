package com.restaurante.dto;

import java.util.List;

import lombok.Data;

@Data
public class ClienteDTO {
	private Long id;
    private String nombre;
    private String correoElectronico;
    private String numeroTelefonico;
    private Long perfilId;
    private List<Long> pedidosIds;
}
