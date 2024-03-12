package com.restaurante.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurante.domain.Cliente;
import com.restaurante.domain.Pedido;
import com.restaurante.domain.Producto;

/**
 * Repositorio de Spring Data JPA para la entidad Pedido.
 */
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    /**
     * Verifica si existe un pedido realizado por un cliente con ciertos productos en una fecha específica.
     * @param cliente   El cliente asociado al pedido.
     * @param productos La lista de productos asociados al pedido.
     * @param fecha     La fecha del pedido.
     * @return true si existe un pedido que cumpla con los criterios especificados, false de lo contrario.
     */
    boolean existsByClienteAndProductosAndFecha(Cliente cliente, List<Producto> productos, LocalDateTime fecha);
}
