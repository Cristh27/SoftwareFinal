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


@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);
    
    
    @GetMapping
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


    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
    	try {
            Cliente cliente = clienteService.buscarPorId(id);
            ClienteDTO departamentoDTO = modelMapper.map(cliente, ClienteDTO.class);

            ApiResponse<ClienteDTO> response = new ApiResponse<>(true, "Cliente obtenido con éxito", departamentoDTO);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, "Error interno del servidor", null));
        }
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody ClienteDTO clienteDTO) throws EntityNotFoundException {
    	try {
            Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
            clienteService.grabar(cliente);

            ClienteDTO savedclienteDTO = modelMapper.map(cliente, ClienteDTO.class);
            ApiResponse<ClienteDTO> response = new ApiResponse<>(true, "Departamento guardado con éxito", savedclienteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, "Error interno del servidor", null));
        }
    }

    

    @DeleteMapping("/{id}")
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

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteDTO>> actualizar(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        try {
            Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
            clienteService.actualizar(id, cliente);

            ClienteDTO updatedDClienteDTO = modelMapper.map(cliente, ClienteDTO.class);
            ApiResponse<ClienteDTO> response = new ApiResponse<>(true, "Cliente actualizado con éxito", updatedDClienteDTO);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, "Error interno del servidor", null));
        }
    }
    
}
