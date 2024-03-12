package com.restaurante.services;

import com.restaurante.domain.Cliente;
import com.restaurante.domain.Perfil;
import com.restaurante.exception.IllegalOperationException;
import com.restaurante.repositories.ClienteRepository;
import com.restaurante.repositories.PerfilRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Implementación del servicio para gestionar perfiles.
 */
@Service
public class PerfilServiceImp implements PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    /**
     * Obtiene una lista de todos los perfiles.
     * @return Una lista de perfiles.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Perfil> listarTodos() {
        return perfilRepository.findAll();
    }

    /**
     * Busca un perfil por su identificador.
     * @param id El identificador del perfil a buscar.
     * @return El perfil encontrado.
     * @throws EntityNotFoundException Si no se encuentra el perfil con el ID especificado.
     */
    @Override
    @Transactional(readOnly = true)
    public Perfil buscarPorId(Long id) throws EntityNotFoundException {
        return perfilRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Perfil no encontrado con ID: " + id));
    }

    /**
     * Guarda un nuevo perfil.
     * @param perfil El perfil a guardar.
     * @return El perfil guardado.
     * @throws IllegalOperationException Si ocurre una operación ilegal al intentar guardar el perfil.
     */
    @Override
    @Transactional
    public Perfil grabar(Perfil perfil) throws IllegalOperationException {
        // Aquí podrías realizar validaciones adicionales antes de guardar el perfil, si es necesario
        return perfilRepository.save(perfil);
    }

    /**
     * Actualiza un perfil existente.
     * @param id El identificador del perfil a actualizar.
     * @param perfil El perfil con los datos actualizados.
     * @return El perfil actualizado.
     * @throws EntityNotFoundException Si no se encuentra el perfil con el ID especificado.
     * @throws IllegalOperationException Si ocurre una operación ilegal al intentar actualizar el perfil.
     */
    @Override
    @Transactional
    public Perfil actualizar(Long id, Perfil perfil) throws EntityNotFoundException, IllegalOperationException {
        // Verificar si el perfil existe
        Perfil perfilExistente = buscarPorId(id);

        // Actualizar los atributos del perfil existente con los valores del perfil proporcionado
        perfilExistente.setPreferencias(perfil.getPreferencias());

        return perfilRepository.save(perfilExistente);
    }

    /**
     * Elimina un perfil.
     * @param id El identificador del perfil a eliminar.
     * @throws EntityNotFoundException Si no se encuentra el perfil con el ID especificado.
     * @throws IllegalOperationException Si ocurre una operación ilegal al intentar eliminar el perfil.
     */
    @Override
    @Transactional
    public void eliminar(Long id) throws EntityNotFoundException, IllegalOperationException {
        // Verificar si el perfil existe
        Perfil perfilExistente = buscarPorId(id);

        // Aquí podrías agregar lógica adicional, como verificar si el perfil está asociado a algún otro objeto antes de eliminarlo

        // Si no necesitas realizar ninguna verificación adicional, puedes eliminar directamente el perfil
        perfilRepository.delete(perfilExistente);
    }
    
}
