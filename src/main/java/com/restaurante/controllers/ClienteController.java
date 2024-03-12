/*
 * @file ClienteController.java
 * @Author Jesus (c)2024
 * @Created 10 mar. 2024,11:13:03
 */

package com.restaurante.controllers;

import com.restaurante.domain.Cliente;
import com.restaurante.dto.ClienteDTO;
import com.restaurante.exception.EntityNotFoundException;
import com.restaurante.exception.IllegalOperationException;
import com.restaurante.services.ClienteService;
import com.restaurante.util.ApiResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para gestionar operaciones relacionadas con los clientes.
 */
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);
    
    
    /**
     * Obtiene todos los clientes.
     *
     * @return ResponseEntity con la lista de clientes y un mensaje de éxito.
     * @throws EntityNotFoundException Si no se encuentran clientes.
     */
    @GetMapping(headers="X-API-VERSION=1.1.0")
    public ResponseEntity<?> obtenerTodos() throws EntityNotFoundException{
        try {
            List<Cliente> clientes = clienteService.listarTodos();
            
            if (clientes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body(new ApiResponse<>(false, "No se encontraron clientes", null));
            }
            
            List<ClienteDTO> clienteDTOs = clientes.stream()
                                                   .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                                                   .collect(Collectors.toList());

            ApiResponse<List<ClienteDTO>> response = new ApiResponse<>(true, "Lista de clientes obtenida con éxito", clienteDTOs);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(new ApiResponse<>(false, "No se encontraron clientes", null));
        } catch (Exception e) {
            // Logging del error
            logger.error("Error al obtener la lista de clientes", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ApiResponse<>(false, "Error interno del servidor", null));
        }
    }


    /**
     * Obtiene un cliente por su ID.
     *
     * @param id El ID del cliente.
     * @return ResponseEntity con el cliente y un mensaje de éxito, o un mensaje de error si no se encuentra el cliente.
     */
    @GetMapping(value="/{id}", headers="X-API-VERSION=1.1.0")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
    	try {
            Cliente cliente = clienteService.buscarPorId(id);
            ClienteDTO clienteDTO = modelMapper.map(cliente, ClienteDTO.class);

            ApiResponse<ClienteDTO> response = new ApiResponse<>(true, "Cliente obtenido con éxito", clienteDTO);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, "Error interno del servidor", null));
        }
    }

    /**
     * Guarda un nuevo cliente.
     *
     * @param clienteDTO El DTO del cliente que se desea guardar.
     * @return ResponseEntity con el cliente guardado y un mensaje de éxito, o un mensaje de error si falla la operación.
     */
    @PostMapping(headers="X-API-VERSION=1.1.0")
    public ResponseEntity<?> guardar(@RequestBody ClienteDTO clienteDTO) {
    	try {
            Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
            clienteService.grabar(cliente);

            ClienteDTO savedClienteDTO = modelMapper.map(cliente, ClienteDTO.class);
            ApiResponse<ClienteDTO> response = new ApiResponse<>(true, "Cliente guardado con éxito", savedClienteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, "Error interno del servidor", null));
        }
    }

    /**
     * Elimina un cliente por su ID.
     *
     * @param id El ID del cliente que se desea eliminar.
     * @return ResponseEntity con un mensaje de éxito después de eliminar el cliente, o un mensaje de error si falla la operación.
     */
    @DeleteMapping(value="/{id}", headers="X-API-VERSION=1.1.0")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            clienteService.eliminar(id);
            ApiResponse<String> response = new ApiResponse<>(true, "Cliente eliminado con éxito", null);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (IllegalOperationException e) {  
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, "Error interno del servidor", null));
        }
    }

    /**
     * Actualiza un cliente por su ID.
     *
     * @param id          El ID del cliente que se desea actualizar.
     * @param clienteDTO  El DTO del cliente con los nuevos datos.
     * @return ResponseEntity con el cliente actualizado y un mensaje de éxito, o un mensaje de error si falla la operación.
     */
    @PutMapping(value="/{id}", headers="X-API-VERSION=1.1.0")
    public ResponseEntity<ApiResponse<ClienteDTO>> actualizar(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        try {
            Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
            clienteService.actualizar(id, cliente);

            ClienteDTO updatedClienteDTO = modelMapper.map(cliente, ClienteDTO.class);
            ApiResponse<ClienteDTO> response = new ApiResponse<>(true, "Cliente actualizado con éxito", updatedClienteDTO);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, "Error interno del servidor", null));
        }
    }
    
}
