/*
 * @file PedidoController.java
 * @Author Jesus (c)2024
 * @Created 12 mar. 2024, 11:33:00
 */

package com.restaurante.controllers;

import com.restaurante.domain.Pedido;
import com.restaurante.dto.PedidoDTO;
import com.restaurante.exception.EntityNotFoundException;
import com.restaurante.exception.IllegalOperationException;
import com.restaurante.services.PedidoService;
import com.restaurante.util.ApiResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para gestionar operaciones relacionadas con los pedidos.
 */
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Obtiene todos los pedidos.
     *
     * @return ResponseEntity con la lista de pedidos y un mensaje de éxito.
     * @throws EntityNotFoundException Si no se encuentran pedidos.
     */
    @GetMapping(headers = "X-API-VERSION=1.1.0")
    public ResponseEntity<?> listarTodos() throws EntityNotFoundException  {
        try {
            List<Pedido> pedidos = pedidoService.listarTodos();
            List<PedidoDTO> pedidosDTO = pedidos.stream()
                    .map(pedido -> modelMapper.map(pedido, PedidoDTO.class))
                    .collect(Collectors.toList());

            ApiResponse<List<PedidoDTO>> response = new ApiResponse<>(true, "Lista de pedidos obtenida con éxito", pedidosDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, "Error interno del servidor", null));
        }
    }

    /**
     * Obtiene un pedido por su ID.
     *
     * @param id El ID del pedido.
     * @return ResponseEntity con el pedido y un mensaje de éxito, o un mensaje de error si no se encuentra el pedido.
     */
    @GetMapping(value = "/{id}", headers = "X-API-VERSION=1.1.0")
    public ResponseEntity<?> buscarPorId(@PathVariable("id") Long id) {
        try {
            Pedido pedido = pedidoService.buscarPorId(id);
            PedidoDTO pedidoDTO = modelMapper.map(pedido, PedidoDTO.class);

            ApiResponse<PedidoDTO> response = new ApiResponse<>(true, "Pedido obtenido con éxito", pedidoDTO);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, "Error interno del servidor", null));
        }
    }

    /**
     * Crea un nuevo pedido.
     *
     * @param pedido El pedido que se desea crear.
     * @return ResponseEntity con el pedido creado y un mensaje de éxito, o un mensaje de error si falla la operación.
     */
    @PostMapping(value = "/crear", headers = "X-API-VERSION=1.1.0")
    public ResponseEntity<?> crearPedido(@RequestBody Pedido pedido) {
        try {
            Pedido nuevoPedido = pedidoService.crearPedido(pedido);
            return new ResponseEntity<>(nuevoPedido, HttpStatus.CREATED);
        } catch (IllegalOperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Actualiza un pedido por su ID.
     *
     * @param id El ID del pedido que se desea actualizar.
     * @param nuevoPedido El pedido con los nuevos datos.
     * @return ResponseEntity con el pedido actualizado y un mensaje de éxito, o un mensaje de error si falla la operación.
     */
    @PutMapping(value = "/{id}", headers = "X-API-VERSION=1.1.0")
    public ResponseEntity<?> actualizarPedido(@PathVariable("id") Long id, @RequestBody Pedido nuevoPedido) {
        try {
            Pedido pedidoActualizado = pedidoService.actualizarPedido(id, nuevoPedido);
            return new ResponseEntity<>(pedidoActualizado, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalOperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Elimina un pedido por su ID.
     *
     * @param id El ID del pedido que se desea eliminar.
     * @return ResponseEntity con un mensaje de éxito después de eliminar el pedido, o un mensaje de error si falla la operación.
     */
    @DeleteMapping(value = "/{id}",headers = "X-API-VERSION=1.1.0")
    public ResponseEntity<Void> eliminarPedido(@PathVariable("id") Long id) {
        try {
            pedidoService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalOperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Asigna un cliente y un producto a un pedido específico.
     *
     * @param idPedido El ID del pedido al que se desea asignar el cliente y el producto.
     * @param idCliente El ID del cliente que se desea asignar al pedido.
     * @param idProducto El ID del producto que se desea asignar al pedido.
     * @return ResponseEntity con el pedido actualizado y un mensaje de éxito, o un mensaje de error si falla la operación.
     */
    @PutMapping(value = "/{idPedido}/cliente/{idCliente}/producto/{idProducto}", headers = "X-API-VERSION=1.1.0")
    public ResponseEntity<?> asignarClienteProducto(@PathVariable("idPedido") Long idPedido,
                                                     @PathVariable("idCliente") Long idCliente,
                                                     @PathVariable("idProducto") Long idProducto) {
        try {
            Pedido pedido = pedidoService.asignarClienteProducto(idPedido, idCliente, idProducto);
            return new ResponseEntity<>(pedido, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalOperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Actualiza el estado de un pedido por su ID.
     *
     * @param id El ID del pedido que se desea actualizar.
     * @param nuevoEstado El nuevo estado del pedido.
     * @return ResponseEntity con el pedido actualizado y un mensaje de éxito, o un mensaje de error si falla la operación.
     */
    @PatchMapping(value = "/{id}/estado",headers = "X-API-VERSION=1.1.0")
    public ResponseEntity<?> actualizarEstado(@PathVariable("id") Long id,
                                               @RequestParam("estado") String nuevoEstado) {
        try {
            Pedido pedido = pedidoService.actualizarEstado(id, nuevoEstado);
            return new ResponseEntity<>(pedido, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalOperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    
   
}
