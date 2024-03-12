package com.restaurante.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.restaurante.domain.Cliente;
import com.restaurante.domain.Pedido;
import com.restaurante.domain.Producto;
import com.restaurante.exception.EntityNotFoundException;
import com.restaurante.exception.ErrorMessage;
import com.restaurante.exception.IllegalOperationException;
import com.restaurante.repositories.ClienteRepository;
import com.restaurante.repositories.PedidoRepository;
import com.restaurante.repositories.ProductoRepository;


/**
 * Implementación del servicio para gestionar pedidos.
 */
@Service
public class PedidoServiceImp implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    private ClienteRepository clienteRepository;
    private ProductoRepository productoRepository;

    @Override
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido buscarPorId(Long id) throws EntityNotFoundException {
    	Optional<Pedido> pedido = pedidoRepository.findById(id);
		if(pedido.isEmpty())throw new EntityNotFoundException(ErrorMessage.PEDIDO_NOT_FOUND);
		return pedido.get();
    }

    

    @Override
    public Pedido actualizarEstado(Long id, String nuevoEstado) throws EntityNotFoundException, IllegalOperationException {
        // Buscar el pedido por su ID
        Pedido pedido = buscarPorId(id);
        
        // Validar que el nuevo estado sea válido
        if (!esEstadoValido(nuevoEstado)) {
            throw new IllegalOperationException("El estado proporcionado no es válido.");
        }
        
        // Actualizar el estado del pedido con el nuevo estado proporcionado
        pedido.setEstado(nuevoEstado);
        
        // Guardar el pedido actualizado en la base de datos
        return pedidoRepository.save(pedido);
    }

    // Método para verificar si un estado es válido
    private boolean esEstadoValido(String estado) {
        // Lista de estados válidos
        List<String> estadosValidos = Arrays.asList("pendiente", "en proceso", "entregado");
        
        // Verificar si el estado proporcionado está en la lista de estados válidos
        return estadosValidos.contains(estado.toLowerCase());
    }
    @Override
    public Pedido actualizarPedido(Long id, Pedido nuevoPedido) throws EntityNotFoundException, IllegalOperationException {
        // Buscar el pedido existente por su ID
        Pedido pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con ID: " + id));

        // Actualizar los detalles del pedido existente con los nuevos detalles proporcionados
        pedidoExistente.setCantidad(nuevoPedido.getCantidad());
        pedidoExistente.setFecha(nuevoPedido.getFecha());
        pedidoExistente.setEstado(nuevoPedido.getEstado());
        pedidoExistente.setCliente(nuevoPedido.getCliente());
        pedidoExistente.setProductos(nuevoPedido.getProductos());

        // Guardar el pedido actualizado en la base de datos
        return pedidoRepository.save(pedidoExistente);
    }


    @Override
    public void eliminar(Long idPedido) throws EntityNotFoundException, IllegalOperationException {
    	// Validar que el departamento exista en la bd
    			Pedido pedido = pedidoRepository.findById(idPedido).orElseThrow(
    					()->new EntityNotFoundException(ErrorMessage.PEDIDO_NOT_FOUND)
    					);
    			
    			if (!(pedido.getCliente()==null)){
    				throw new IllegalOperationException("El pedido tiene clientes asignados");
    			}
    			if (!(pedido.getProductos()==null)) {
    				throw new IllegalOperationException("El pedido tiene productos asignados");
    			}
    							
    			pedidoRepository.deleteById(idPedido);
    }
    @Override
    public Pedido asignarClienteProducto(Long idPedido, Long idCliente, Long idProducto) throws EntityNotFoundException, IllegalOperationException {
        // Obtener el pedido por su ID
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.PEDIDO_NOT_FOUND));

        // Obtener el cliente por su ID
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.CLIENTE_NOT_FOUND));

        // Obtener el producto por su ID
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));

        // Asignar el cliente al pedido
        pedido.setCliente(cliente);

        // Crear una lista de productos y agregar el producto
        List<Producto> productos = new ArrayList<>();
        productos.add(producto);

        // Asignar la lista de productos al pedido
        pedido.setProductos(productos);

        // Guardar los cambios en la base de datos
        pedidoRepository.save(pedido);

        return pedido;
    }

    @Override
    public Pedido crearPedido(Pedido pedido) throws IllegalOperationException {
        // Verificar si ya existe un pedido con la misma combinación de cliente, productos y fecha
        boolean pedidoExistente = pedidoRepository.existsByClienteAndProductosAndFecha(pedido.getCliente(), pedido.getProductos(), pedido.getFecha());
        
        // Si ya existe un pedido con la misma combinación, lanzar una excepción
        if (pedidoExistente) {
            throw new IllegalOperationException("Ya existe un pedido similar en la base de datos.");
        }
        
        // Guardar el nuevo pedido en la base de datos
        return pedidoRepository.save(pedido);
    }

}
