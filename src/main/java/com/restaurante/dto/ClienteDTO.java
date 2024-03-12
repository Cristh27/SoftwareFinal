package com.restaurante.dto;

import java.util.List;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClienteDTO {
	/** Id del cliente. */
	
	private Long id;
	
	/** Nombre del cliente. */
	@Size(max=50)
    private String nombre;
	/** Correo del cliente. */
	@Size(max=50)
    private String correoElectronico;
    /** NumeroTelefonico del cliente. */
	@Digits(integer =9, fraction = 0)
    private String numeroTelefonico;
    
	/** Id del perfil. */
    private Long perfilId;
    /** Pedidos del cliente. */
    private List<Long> pedidosIds;
}
