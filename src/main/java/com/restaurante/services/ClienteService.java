package com.restaurante.services;

import java.util.List;
import com.restaurante.domain.Cliente;
import com.restaurante.exception.IllegalOperationException;
import com.restaurante.exception.EntityNotFoundException;

/**
 * Interfaz que define los servicios relacionados con la entidad Cliente.
 */
public interface ClienteService {

    /**
     * Obtiene una lista de todos los clientes.
     * 
     * @return Una lista de todos los clientes.
     * @throws EntityNotFoundException Si no se encuentran clientes en la base de datos.
     */
    List<Cliente> listarTodos() throws EntityNotFoundException;

    /**
     * Busca un cliente por su identificador.
     * 
     * @param id El identificador del cliente a buscar.
     * @return El cliente encontrado.
     * @throws EntityNotFoundException Si no se encuentra el cliente con el ID especificado.
     * @throws IllegalOperationException Si ocurre una operación ilegal al intentar buscar el cliente.
     */
    Cliente buscarPorId(Long id) throws EntityNotFoundException, IllegalOperationException;

    /**
     * Guarda un nuevo cliente en la base de datos.
     * 
     * @param cliente El cliente a guardar.
     * @return El cliente guardado.
     * @throws IllegalOperationException Si ocurre una operación ilegal al intentar guardar el cliente.
     * @throws EntityNotFoundException Si no se encuentra alguna entidad relacionada con el cliente.
     */
    Cliente grabar(Cliente cliente) throws IllegalOperationException, EntityNotFoundException;

    /**
     * Actualiza un cliente existente en la base de datos.
     * 
     * @param id El identificador del cliente a actualizar.
     * @param cliente El cliente con los datos actualizados.
     * @return El cliente actualizado.
     * @throws EntityNotFoundException Si no se encuentra el cliente con el ID especificado.
     * @throws IllegalOperationException Si ocurre una operación ilegal al intentar actualizar el cliente.
     */
    Cliente actualizar(Long id, Cliente cliente) throws EntityNotFoundException, IllegalOperationException;

    /**
     * Elimina un cliente de la base de datos.
     * 
     * @param id El identificador del cliente a eliminar.
     * @throws EntityNotFoundException Si no se encuentra el cliente con el ID especificado.
     * @throws IllegalOperationException Si ocurre una operación ilegal al intentar eliminar el cliente.
     */
    void eliminar(Long id) throws EntityNotFoundException, IllegalOperationException;
    
    /**
     * Busca clientes por su nombre en la base de datos.
     * 
     * @param nombre El nombre del cliente a buscar.
     * @return Una lista de clientes que coinciden con el nombre especificado.
     */
    List<Cliente> buscarPorNombre(String nombre);
}
