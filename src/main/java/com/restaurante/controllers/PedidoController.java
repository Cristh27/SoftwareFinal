package com.restaurante.controllers;


import java.util.List;
import java.util.stream.Collectors;

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

import com.restaurante.domain.Pedido;
import com.restaurante.dto.PedidoDTO;
import com.restaurante.exception.EntityNotFoundException;
import com.restaurante.exception.IllegalOperationException;
import com.restaurante.services.PedidoService;
import com.restaurante.util.ApiResponse;



@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
	@Autowired
    private PedidoService pedidoService;
    @Autowired
    private ModelMapper modelMapper;
    

    @GetMapping
    public ResponseEntity<?> listarTodos()throws EntityNotFoundException  {
    	try {
            List<Pedido> pedidos = pedidoService.listarTodos();
            List<PedidoDTO> pedidosDTO = pedidos.stream()
                    .map(departamento -> modelMapper.map(departamento, PedidoDTO.class))
                    .collect(Collectors.toList());

            ApiResponse<List<PedidoDTO>> response = new ApiResponse<>(true, "Lista de pedidos obtenida con éxito", pedidosDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, "Error interno del servidor", null));
        }
    }

    @GetMapping("/{id}")
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

    @PostMapping("/crear")
    public ResponseEntity<?> crearPedido(@RequestBody Pedido pedido) {
        try {
            Pedido nuevoPedido = pedidoService.crearPedido(pedido);
            return new ResponseEntity<>(nuevoPedido, HttpStatus.CREATED);
        } catch (IllegalOperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizarPedido(@PathVariable("id") Long id, @RequestBody Pedido nuevoPedido) {
        try {
            Pedido pedidoActualizado = pedidoService.actualizarPedido(id, nuevoPedido);
            return new ResponseEntity<>(pedidoActualizado, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalOperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
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

    @PutMapping("/{idPedido}/cliente/{idCliente}/producto/{idProducto}")
    public ResponseEntity<Pedido> asignarClienteProducto(@PathVariable("idPedido") Long idPedido,
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

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Pedido> actualizarEstado(@PathVariable("id") Long id,
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
