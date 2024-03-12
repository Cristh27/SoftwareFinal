package com.restaurante.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurante.domain.Cliente;
import com.restaurante.domain.Pedido;
import com.restaurante.domain.Producto;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	boolean existsByClienteAndProductosAndFecha(Cliente cliente, List<Producto> productos, LocalDateTime fecha);
}
