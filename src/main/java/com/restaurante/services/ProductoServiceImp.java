package com.restaurante.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import com.restaurante.domain.Producto;
import com.restaurante.exception.EntityNotFoundException;
import com.restaurante.exception.ErrorMessage;
import com.restaurante.exception.IllegalOperationException;
import com.restaurante.repositories.ProductoRepository;

/**
 * Implementación del servicio para la gestión de productos en el restaurante.
 */
@Service
public class ProductoServiceImp implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    /**
     * Obtiene una lista de todos los productos.
     * @return Una lista de objetos Producto.
     */
    @Override
    @Transactional
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    /**
     * Busca un producto por su identificador.
     * @param id El identificador del producto a buscar.
     * @return El producto encontrado.
     * @throws EntityNotFoundException Si no se encuentra el producto con el ID especificado.
     */
    @Override
    @Transactional
    public Producto buscarPorId(Long id) throws EntityNotFoundException {
        Optional<Producto> producto = productoRepository.findById(id);
        if (producto.isEmpty()) throw new EntityNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND);
        return producto.get();
    }

    /**
     * Guarda un nuevo producto en el sistema.
     * @param producto El producto a guardar.
     * @return El producto guardado.
     * @throws IllegalOperationException Si ocurre una operación ilegal al intentar guardar el producto.
     */
    @Override
    @Transactional
    public Producto grabar(Producto producto) throws IllegalOperationException {
        if (!productoRepository.findByNombre(producto.getNombre()).isEmpty()) {
            throw new IllegalOperationException("El nombre del producto ya existe");
        }
        return productoRepository.save(producto);
    }

    /**
     * Actualiza un producto existente en el sistema.
     * @param id El identificador del producto a actualizar.
     * @param producto El producto con los datos actualizados.
     * @return El producto actualizado.
     * @throws EntityNotFoundException Si no se encuentra el producto con el ID especificado.
     * @throws IllegalOperationException Si ocurre una operación ilegal al intentar actualizar el producto.
     */
    @Override
    @Transactional
    public Producto actualizar(Long id, Producto producto) throws EntityNotFoundException, IllegalOperationException {
        // Verificar si el producto existe
        Producto productoExistente = buscarPorId(id);

        // Actualizar los atributos del producto existente con los valores del producto proporcionado
        productoExistente.setNombre(producto.getNombre());
        productoExistente.setDescripcion(producto.getDescripcion());
        productoExistente.setPrecio(producto.getPrecio());

        return productoRepository.save(productoExistente);
    }

    /**
     * Elimina un producto del sistema.
     * @param idProducto El identificador del producto a eliminar.
     * @throws EntityNotFoundException Si no se encuentra el producto con el ID especificado.
     * @throws IllegalOperationException Si el producto tiene pedidos asignados.
     */
    @Override
    @Transactional
    public void eliminar(Long idProducto) throws EntityNotFoundException, IllegalOperationException {
        // Validar que el producto exista en la base de datos
        Producto producto = productoRepository.findById(idProducto).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND)
        );

        if (!producto.getPedidos().isEmpty()) {
            throw new IllegalOperationException("El producto tiene pedidos asignados");
        }

        productoRepository.deleteById(idProducto);
    }

    /**
     * Asigna un producto como variante de otro producto existente en el sistema.
     * @param idVariante El identificador del producto que será la variante.
     * @param idProducto El identificador del producto al que se asignará la variante.
     * @return El producto actualizado con la variante asignada.
     * @throws EntityNotFoundException Si no se encuentran uno o ambos productos con los ID especificados.
     */
    @Override
    @Transactional
    public Producto asignarProductoAVariante(Long idVariante, Long idProducto) throws EntityNotFoundException {
        Producto variante = productoRepository.findById(idVariante)
                .orElseThrow(() -> new EntityNotFoundException("Variante no encontrada con ID: " + idVariante));

        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + idProducto));

        producto.setVariante(variante);
        return productoRepository.save(producto);
    }

    /**
     * Crea una variante de producto en el sistema.
     * @param variante El producto que será una variante.
     * @return La variante creada.
     */
    @Override
    @Transactional
    public Producto crearVariante(Producto variante) {
        return productoRepository.save(variante);
    }

}
