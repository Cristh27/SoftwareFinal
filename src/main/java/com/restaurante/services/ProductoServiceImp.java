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

@Service
public class ProductoServiceImp implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    @Transactional
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    @Override
    @Transactional
    public Producto buscarPorId(Long id) throws EntityNotFoundException {
        Optional<Producto> producto = productoRepository.findById(id);
        if (producto.isEmpty()) throw new EntityNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND);
        return producto.get();
    }

    @Override
    @Transactional
    public Producto grabar(Producto producto) throws IllegalOperationException {
        if (!productoRepository.findByNombre(producto.getNombre()).isEmpty()) {
            throw new IllegalOperationException("El nombre del producto ya existe");
        }
        return productoRepository.save(producto);
    }

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

    @Override
    @Transactional
    public void eliminar(Long idProducto) throws EntityNotFoundException, IllegalOperationException {
        // Validar que el departamento exista en la bd
        Producto producto = productoRepository.findById(idProducto).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND)
        );

        if (!producto.getPedidos().isEmpty()) {
            throw new IllegalOperationException("El producto tiene pedidos asignados");
        }

        productoRepository.deleteById(idProducto);
    }


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

    @Override
    @Transactional
    public Producto crearVariante(Producto variante) {
        return productoRepository.save(variante);
    }

    
}
