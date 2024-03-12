package com.restaurante.services;

import java.util.List;
import com.restaurante.domain.Perfil;
import com.restaurante.exception.IllegalOperationException;

import jakarta.persistence.EntityNotFoundException;

public interface PerfilService {

    List<Perfil> listarTodos();

    Perfil buscarPorId(Long id) throws EntityNotFoundException;

    Perfil grabar(Perfil perfil) throws IllegalOperationException;

    Perfil actualizar(Long id, Perfil perfil) throws EntityNotFoundException, IllegalOperationException;

    void eliminar(Long id) throws EntityNotFoundException, IllegalOperationException;
    
    
}
