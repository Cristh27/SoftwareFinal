package com.restaurante.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.restaurante.domain.Producto;
import java.util.Optional;

/**
 * Repositorio de Spring Data JPA para la entidad Producto.
 * Proporciona métodos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * en la base de datos asociada a la entidad Producto.
 */
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    /**
     * Busca un producto por su nombre en la base de datos.
     * 
     * @param nombre El nombre del producto a buscar.
     * @return Un Optional que puede contener el producto si se encuentra, o vacío si no se encuentra.
     */
    Optional<Producto> findByNombre(String nombre);
}
