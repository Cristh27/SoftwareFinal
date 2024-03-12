/*
 * @file ProductoController.java
 * @Author Jesus (c)2024
 * @Created 12 mar. 2024, 11:33:00
 * @version 1.1.0
 */

package com.restaurante.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restaurante.domain.Producto;
import com.restaurante.dto.ProductoDTO;
import com.restaurante.services.ProductoService;
import com.restaurante.util.ApiResponse;
import com.restaurante.exception.EntityNotFoundException;
import com.restaurante.exception.IllegalOperationException;

/**
 * Controlador REST para gestionar operaciones relacionadas con los productos.
 * Version: 1.1.0
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Obtiene todos los productos.
     *
     * @return ResponseEntity con la lista de productos.
     */
    @GetMapping(headers = "X-API-VERSION=1.1.0")
    public ResponseEntity<?> listarProductos() throws EntityNotFoundException {
    	try {
            List<Producto> productos = productoService.listarTodos();
            List<ProductoDTO> productosDTOS = productos.stream()
                    .map(producto -> modelMapper.map(producto, ProductoDTO.class))
                    .collect(Collectors.toList());

            ApiResponse<List<ProductoDTO>> response = new ApiResponse<>(true, "Lista de productos obtenida con éxito", productosDTOS);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, "Error interno del servidor", null));
        }
    }

    /**
     * Obtiene un producto por su ID.
     *
     * @param id El ID del producto.
     * @return ResponseEntity con el producto encontrado o un mensaje de error si no se encuentra.
     */
    @GetMapping(value = "/{id}", headers = "X-API-VERSION=1.1.0")
    public ResponseEntity<?> obtenerProductoPorId(@PathVariable Long id) {
    	try {
            Producto producto = productoService.buscarPorId(id);
            ProductoDTO productoDTO = modelMapper.map(producto, ProductoDTO.class);

            ApiResponse<ProductoDTO> response = new ApiResponse<>(true, "Productos obtenidos con éxito", productoDTO);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, "Error interno del servidor", null));
        }
    }

    /**
     * Crea un nuevo producto.
     *
     * @param productoDTO El producto a crear.
     * @return ResponseEntity con el nuevo producto creado o un mensaje de error si falla la operación.
     */
    @PostMapping(headers = "X-API-VERSION=1.1.0")
    public ResponseEntity<?> guardarProducto(@RequestBody ProductoDTO productoDTO) {
    	 try {
             Producto producto = modelMapper.map(productoDTO, Producto.class);
             productoService.grabar(producto);

             ProductoDTO savedProductoDTO = modelMapper.map(producto, ProductoDTO.class);
             ApiResponse<ProductoDTO> response = new ApiResponse<>(true, "Producto guardado con éxito", savedProductoDTO);
             return ResponseEntity.status(HttpStatus.CREATED).body(response);
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, "Error interno del servidor", null));
         }
    }

    /**
     * Actualiza un producto por su ID.
     *
     * @param id           El ID del producto a actualizar.
     * @param productoDTO  El producto actualizado.
     * @return ResponseEntity con el producto actualizado o un mensaje de error si falla la operación.
     */
    @PutMapping(value = "/{id}", headers = "X-API-VERSION=1.1.0")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
    	 try {
             Producto producto = modelMapper.map(productoDTO, Producto.class);
             productoService.actualizar(id, producto);

             ProductoDTO updatedProductoDTO = modelMapper.map(producto, ProductoDTO.class);
             ApiResponse<ProductoDTO> response = new ApiResponse<>(true, "Producto actualizado con éxito", updatedProductoDTO);
             return ResponseEntity.ok(response);
         } catch (EntityNotFoundException e) {
         	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, e.getMessage(), null));
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, "Error interno del servidor", null));
         }
    }

    /**
     * Elimina un producto por su ID.
     *
     * @param id El ID del producto a eliminar.
     * @return ResponseEntity con un mensaje de éxito o un mensaje de error si falla la operación.
     */
    @DeleteMapping(value = "/{id}", headers = "X-API-VERSION=1.1.0")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
    	try {
            productoService.eliminar(id);
            ApiResponse<String> response = new ApiResponse<>(true, "Producto eliminado con éxito", null);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (IllegalOperationException e) {  
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, "Error interno del servidor", null));
        }
    }
    
   
}
