package com.restaurante.services;

import java.util.List;
import com.restaurante.domain.Perfil;
import com.restaurante.exception.IllegalOperationException;
import com.restaurante.exception.EntityNotFoundException;

/**
 * Interfaz que define los métodos para el servicio de gestión de perfiles.
 */
public interface PerfilService {

    /**
     * Obtiene una lista de todos los perfiles.
     * @return Una lista de perfiles.
     */
    List<Perfil> listarTodos();

    /**
     * Busca un perfil por su identificador.
     * @param id El identificador del perfil a buscar.
     * @return El perfil encontrado.
     * @throws EntityNotFoundException Si no se encuentra el perfil con el ID especificado.
     */
    Perfil buscarPorId(Long id) throws EntityNotFoundException;

    /**
     * Guarda un nuevo perfil.
     * @param perfil El perfil a guardar.
     * @return El perfil guardado.
     * @throws IllegalOperationException Si ocurre una operación ilegal al intentar guardar el perfil.
     */
    Perfil grabar(Perfil perfil) throws IllegalOperationException;

    /**
     * Actualiza un perfil existente.
     * @param id El identificador del perfil a actualizar.
     * @param perfil El perfil con los datos actualizados.
     * @return El perfil actualizado.
     * @throws EntityNotFoundException Si no se encuentra el perfil con el ID especificado.
     * @throws IllegalOperationException Si ocurre una operación ilegal al intentar actualizar el perfil.
     */
    Perfil actualizar(Long id, Perfil perfil) throws EntityNotFoundException, IllegalOperationException;

    /**
     * Elimina un perfil.
     * @param id El identificador del perfil a eliminar.
     * @throws EntityNotFoundException Si no se encuentra el perfil con el ID especificado.
     * @throws IllegalOperationException Si ocurre una operación ilegal al intentar eliminar el perfil.
     */
    void eliminar(Long id) throws EntityNotFoundException, IllegalOperationException;
}
