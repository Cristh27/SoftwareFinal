package com.restaurante.controllers;

import java.util.List;
import java.util.stream.Collectors;

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


import com.restaurante.domain.Producto;
import com.restaurante.dto.ProductoDTO;
import com.restaurante.services.ProductoService;
import com.restaurante.util.ApiResponse;
import com.restaurante.exception.EntityNotFoundException;
import com.restaurante.exception.IllegalOperationException;


/**
 * Controlador para la entidad Producto
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Maneja las solicitudes GET para obtener todos los productos.
     *
     * @return ResponseEntity con la lista de productos.
     */
    @GetMapping
    public ResponseEntity<?> listarProductos() throws EntityNotFoundException {
    	try {
            List<Producto> productos = productoService.listarTodos();
            List<ProductoDTO> productosDTOS = productos.stream()
                    .map(departamento -> modelMapper.map(departamento, ProductoDTO.class))
                    .collect(Collectors.toList());

            ApiResponse<List<ProductoDTO>> response = new ApiResponse<>(true, "Lista de productos obtenida con éxito", productosDTOS);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, "Error interno del servidor", null));
        }
    }

    /**
     * Maneja las solicitudes GET para obtener un producto por su ID.
     *
     * @param id El ID del producto.
     * @return ResponseEntity con el producto encontrado o un mensaje de error si no se encuentra.
     */
    @GetMapping("/{id}")
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
     * Maneja las solicitudes POST para crear un nuevo producto.
     *
     * @param producto El producto a crear.
     * @return ResponseEntity con el nuevo producto creado o un mensaje de error si falla la operación.
     */
    @PostMapping
    public ResponseEntity<?> guardarProducto(@RequestBody Producto productoDTO) {
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
     * Maneja las solicitudes PUT para actualizar un producto.
     *
     * @param id       El ID del producto a actualizar.
     * @param producto El producto actualizado.
     * @return ResponseEntity con el producto actualizado o un mensaje de error si falla la operación.
     */
    @PutMapping("/{id}")
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
     * Maneja las solicitudes DELETE para eliminar un producto.
     *
     * @param id El ID del producto a eliminar.
     * @return ResponseEntity con un mensaje de éxito o un mensaje de error si falla la operación.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
    	try {
            productoService.eliminar(id);
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
     * Maneja las solicitudes PUT para asignar una variante a un producto.
     *
     * @param idProducto  El ID del producto principal.
     * @param idVariante  El ID de la variante que se asignará al producto principal.
     * @return ResponseEntity con el producto principal actualizado o un mensaje de error si falla la operación.
     */
    /**
     * Maneja las solicitudes PUT para asignar una variante a un producto.
     *
     * @param idProducto  El ID del producto principal.
     * @param idVariante  El ID de la variante que se asignará al producto principal.
     * @return ResponseEntity con el producto principal actualizado o un mensaje de error si falla la operación.
     */
    @PutMapping("/{idProducto}/variante/{idVariante}")
    public ResponseEntity<?> asignarVariante(@PathVariable Long idProducto, @PathVariable Long idVariante) {
        try {
            Producto productoPrincipal = productoService.buscarPorId(idProducto);
            Producto variante = productoService.buscarPorId(idVariante);

            productoPrincipal.setVariante(variante);
            productoService.grabar(productoPrincipal);

            ProductoDTO productoDTO = modelMapper.map(productoPrincipal, ProductoDTO.class);
            return ResponseEntity.ok(new ApiResponse<>(true, "Variante asignada con éxito al producto principal", productoDTO));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (IllegalOperationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, "Error interno del servidor", null));
        }
    }

    /**
     * Maneja las solicitudes POST para crear una variante de un producto existente.
     *
     * @param variante El objeto Producto que representa la variante a crear.
     * @return ResponseEntity con la variante creada o un mensaje de error si falla la operación.
     */
    @PostMapping("/variantes")
    public ResponseEntity<?> crearVariante(@RequestBody Producto variante) {
        try {
            Producto nuevaVariante = productoService.crearVariante(variante);
            ProductoDTO varianteDTO = modelMapper.map(nuevaVariante, ProductoDTO.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "Variante creada con éxito", varianteDTO));
        } catch (IllegalOperationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, "Error interno del servidor", null));
        }
    }
    /**
     * Maneja las solicitudes GET para obtener las variantes de un producto principal.
     *
     * @param id El ID del producto principal.
     * @return ResponseEntity con la lista de variantes del producto principal o un mensaje de error si no se encuentra.
     */
    



}
