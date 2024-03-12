package com.restaurante.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurante.domain.Cliente;

/**
 * Repositorio de Spring Data JPA para la entidad Cliente.
 */
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    /**
     * Método para buscar clientes por su nombre.
     * @param nombre El nombre del cliente a buscar.
     * @return Una lista de clientes que coinciden con el nombre especificado.
     */
    List<Cliente> findByNombre(String nombre);
    
    
}
