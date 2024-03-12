/*
 * @file PerfilController.java
 * @Author Jesus (c)2024
 * @Created 12 mar. 2024, 11:33:00
 * @version 1.1.0
 */

package com.restaurante.controllers;

import com.restaurante.domain.Perfil;
import com.restaurante.exception.ErrorResponse;
import com.restaurante.exception.IllegalOperationException;
import com.restaurante.services.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con los perfiles de usuario.
 * Version: 1.1.0
 */
@RestController
@RequestMapping("/api/perfiles")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    /**
     * Obtiene todos los perfiles de usuario.
     *
     * @return ResponseEntity con la lista de perfiles o sin contenido si no hay perfiles.
     */
    @GetMapping(headers = "X-API-VERSION=1.1.0")
    public ResponseEntity<?> obtenerTodos() {
        try {
            List<Perfil> perfiles = perfilService.listarTodos();
            if (perfiles == null || perfiles.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(perfiles);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error interno del servidor"));
        }
    }

    /**
     * Obtiene un perfil de usuario por su ID.
     *
     * @param id El ID del perfil.
     * @return ResponseEntity con el perfil encontrado o un mensaje de error si no se encuentra.
     */
    @GetMapping(value = "/{id}", headers = "X-API-VERSION=1.1.0")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            Perfil perfil = perfilService.buscarPorId(id);
            return ResponseEntity.ok(perfil);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error interno del servidor"));
        }
    }

    /**
     * Crea un nuevo perfil de usuario.
     *
     * @param perfil El perfil a crear.
     * @return ResponseEntity con el nuevo perfil creado o un mensaje de error si falla la operación.
     */
    @PostMapping(headers = "X-API-VERSION=1.1.0")
    public ResponseEntity<?> guardar(@RequestBody Perfil perfil) {
        try {
            Perfil nuevoPerfil = perfilService.grabar(perfil);
            return new ResponseEntity<>(nuevoPerfil, HttpStatus.CREATED);
        } catch (IllegalOperationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("El cliente ya tiene un perfil"));
        }
    }

    /**
     * Actualiza un perfil de usuario por su ID.
     *
     * @param id     El ID del perfil a actualizar.
     * @param perfil El perfil actualizado.
     * @return ResponseEntity con el perfil actualizado o un mensaje de error si falla la operación.
     */
    @PutMapping(value = "/{id}", headers = "X-API-VERSION=1.1.0")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Perfil perfil) {
        try {
            Perfil perfilActualizado = perfilService.actualizar(id, perfil);
            return ResponseEntity.ok(perfilActualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        } catch (IllegalOperationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error interno del servidor"));
        }
    }

    /**
     * Elimina un perfil de usuario por su ID.
     *
     * @param id El ID del perfil a eliminar.
     * @return ResponseEntity con un mensaje de éxito o un mensaje de error si falla la operación.
     */
    @DeleteMapping(value = "/{id}", headers = "X-API-VERSION=1.1.0")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            perfilService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        } catch (IllegalOperationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error interno del servidor"));
        }
    }
}
