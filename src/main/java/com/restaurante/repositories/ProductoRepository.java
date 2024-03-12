package com.restaurante.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurante.domain.Producto;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findByNombre(String nombre);
}
