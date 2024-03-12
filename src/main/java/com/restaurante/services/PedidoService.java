package com.restaurante.services;

import java.util.List;

import com.restaurante.domain.Pedido;
import com.restaurante.exception.IllegalOperationException;
import com.restaurante.exception.EntityNotFoundException;

/**
 * Interfaz que define los métodos para el servicio de gestión de pedidos.
 */
public interface PedidoService {
    /**
     * Obtiene una lista de todos los pedidos.
     * @return Una lista de pedidos.
     */
    List<Pedido> listarTodos();

    /**
     * Busca un pedido por su identificador.
     * @param id El identificador del pedido a buscar.
     * @return El pedido encontrado.
     * @throws EntityNotFoundException Si no se encuentra el pedido con el ID especificado.
     */
    Pedido buscarPorId(Long id) throws EntityNotFoundException;

    /**
     * Crea un nuevo pedido.
     * @param pedido El pedido a crear.
     * @return El pedido creado.
     * @throws IllegalOperationException Si ocurre una operación ilegal al intentar crear el pedido.
     */
    Pedido crearPedido(Pedido pedido) throws IllegalOperationException;

    /**
     * Actualiza el estado de un pedido.
     * @param id El identificador del pedido a actualizar.
     * @param nuevoEstado El nuevo estado del pedido.
     * @return El pedido actualizado.
     * @throws EntityNotFoundException Si no se encuentra el pedido con el ID especificado.
     * @throws IllegalOperationException Si ocurre una operación ilegal al intentar actualizar el pedido.
     */
    Pedido actualizarEstado(Long id, String nuevoEstado) throws EntityNotFoundException, IllegalOperationException;

    /**
     * Elimina un pedido.
     * @param id El identificador del pedido a eliminar.
     * @throws EntityNotFoundException Si no se encuentra el pedido con el ID especificado.
     * @throws IllegalOperationException Si ocurre una operación ilegal al intentar eliminar el pedido.
     */
    void eliminar(Long id) throws EntityNotFoundException, IllegalOperationException;
    
    /**
     * Actualiza los detalles de un pedido existente.
     * @param id El ID del pedido a actualizar.
     * @param nuevoPedido El objeto Pedido con los nuevos detalles del pedido.
     * @return El pedido actualizado.
     * @throws EntityNotFoundException Si no se encuentra el pedido con el ID especificado.
     * @throws IllegalOperationException Si ocurre una operación ilegal al intentar actualizar el pedido.
     */
    Pedido actualizarPedido(Long id, Pedido nuevoPedido) throws EntityNotFoundException, IllegalOperationException;

    Pedido asignarClienteProducto(Long idPedido, Long idCliente, Long idProducto) throws EntityNotFoundException, IllegalOperationException;
    
}
