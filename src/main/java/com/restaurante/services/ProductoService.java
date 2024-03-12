package com.restaurante.services;

import com.restaurante.domain.Producto;
import com.restaurante.exception.EntityNotFoundException;
import com.restaurante.exception.IllegalOperationException;

import java.util.List;

public interface ProductoService {
    List<Producto> listarTodos();
    Producto buscarPorId(Long id) throws EntityNotFoundException;
    Producto grabar(Producto producto) throws IllegalOperationException;
    Producto actualizar(Long id, Producto producto) throws EntityNotFoundException, IllegalOperationException;
    void eliminar(Long id) throws EntityNotFoundException, IllegalOperationException;
    Producto asignarProductoAVariante(Long idVariante, Long idProducto) throws EntityNotFoundException;
    Producto crearVariante(Producto variante) throws IllegalOperationException, EntityNotFoundException;
    
}
