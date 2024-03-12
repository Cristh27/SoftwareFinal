package com.restaurante.dto;

import java.util.List;

import com.restaurante.domain.Producto;

import lombok.Data;

@Data
public class ProductoDTO {
	 private Long id;
	    private String nombre;
	    private String descripcion;
	    private double precio;
	    private ProductoDTO variante;
	    private List<Producto> pedidosIds;
}
