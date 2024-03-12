package com.restaurante.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.restaurante.domain.Cliente;
import com.restaurante.domain.Producto;

import lombok.Data;

/**
 * DTO que representa un pedido en el sistema.
 */
@Data
public class PedidoDTO {
    private Long id;
    private int cantidad;
    private LocalDateTime fecha;
    private String estado;
    private Cliente cliente;
    private List<Producto> productos;
}

