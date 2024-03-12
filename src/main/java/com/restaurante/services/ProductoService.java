package com.restaurante.services;

import com.restaurante.domain.Producto;
import com.restaurante.exception.EntityNotFoundException;
import com.restaurante.exception.IllegalOperationException;

import java.util.List;

/**
 * Esta interfaz define los métodos para el servicio de gestión de productos en el restaurante.
 */
public interface ProductoService {

    /**
     * Obtiene una lista de todos los productos.
     * @return Una lista de objetos Producto.
     */
    List<Producto> listarTodos();

    /**
     * Busca un producto por su identificador.
     * @param id El identificador del producto a buscar.
     * @return El producto encontrado.
     * @throws EntityNotFoundException Si no se encuentra el producto con el ID especificado.
     */
    Producto buscarPorId(Long id) throws EntityNotFoundException;

    /**
     * Guarda un nuevo producto en el sistema.
     * @param producto El producto a guardar.
     * @return El producto guardado.
     * @throws IllegalOperationException Si ocurre una operación ilegal al intentar guardar el producto.
     */
    Producto grabar(Producto producto) throws IllegalOperationException;

    /**
     * Actualiza un producto existente en el sistema.
     * @param id El identificador del producto a actualizar.
     * @param producto El producto con los datos actualizados.
     * @return El producto actualizado.
     * @throws EntityNotFoundException Si no se encuentra el producto con el ID especificado.
     * @throws IllegalOperationException Si ocurre una operación ilegal al intentar actualizar el producto.
     */
    Producto actualizar(Long id, Producto producto) throws EntityNotFoundException, IllegalOperationException;

    /**
     * Elimina un producto del sistema.
     * @param id El identificador del producto a eliminar.
     * @throws EntityNotFoundException Si no se encuentra el producto con el ID especificado.
     * @throws IllegalOperationException Si ocurre una operación ilegal al intentar eliminar el producto.
     */
    void eliminar(Long id) throws EntityNotFoundException, IllegalOperationException;

    /**
     * Asigna un producto como variante de otro producto existente en el sistema.
     * @param idVariante El identificador del producto que será la variante.
     * @param idProducto El identificador del producto al que se asignará la variante.
     * @return El producto actualizado con la variante asignada.
     * @throws EntityNotFoundException Si no se encuentran uno o ambos productos con los ID especificados.
     */
    Producto asignarProductoAVariante(Long idVariante, Long idProducto) throws EntityNotFoundException;

    /**
     * Crea una variante de producto en el sistema.
     * @param variante El producto que será una variante.
     * @return La variante creada.
     * @throws IllegalOperationException Si ocurre una operación ilegal al intentar crear la variante.
     * @throws EntityNotFoundException Si no se encuentra el producto base al que se asignará la variante.
     */
    Producto crearVariante(Producto variante) throws IllegalOperationException, EntityNotFoundException;
}
