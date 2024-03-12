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

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    /**
     * Obtiene una lista de todos los pedidos.
     * @return Una lista de pedidos.
     */
    @Override
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    /**
     * Busca un pedido por su identificador.
     * @param id El identificador del pedido a buscar.
     * @return El pedido encontrado.
     * @throws EntityNotFoundException Si no se encuentra el pedido con el ID especificado.
     */
    @Override
    public Pedido buscarPorId(Long id) throws EntityNotFoundException {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        if (pedido.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.PEDIDO_NOT_FOUND);
        return pedido.get();
    }

    /**
     * Actualiza el estado de un pedido.
     * @param id El identificador del pedido a actualizar.
     * @param nuevoEstado El nuevo estado del pedido.
     * @return El pedido actualizado.
     * @throws EntityNotFoundException Si no se encuentra el pedido con el ID especificado.
     * @throws IllegalOperationException Si el nuevo estado proporcionado no es válido.
     */
    @Override
    public Pedido actualizarEstado(Long id, String nuevoEstado) throws EntityNotFoundException, IllegalOperationException {
        Pedido pedido = buscarPorId(id);
        
        if (!esEstadoValido(nuevoEstado)) {
            throw new IllegalOperationException("El estado proporcionado no es válido.");
        }
        
        pedido.setEstado(nuevoEstado);
        return pedidoRepository.save(pedido);
    }

    /**
     * Actualiza los detalles de un pedido existente.
     * @param id El ID del pedido a actualizar.
     * @param nuevoPedido El objeto Pedido con los nuevos detalles del pedido.
     * @return El pedido actualizado.
     * @throws EntityNotFoundException Si no se encuentra el pedido con el ID especificado.
     */
    @Override
    public Pedido actualizarPedido(Long id, Pedido nuevoPedido) throws EntityNotFoundException {
        Pedido pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con ID: " + id));

        pedidoExistente.setCantidad(nuevoPedido.getCantidad());
        pedidoExistente.setFecha(nuevoPedido.getFecha());
        pedidoExistente.setEstado(nuevoPedido.getEstado());
        pedidoExistente.setCliente(nuevoPedido.getCliente());
        pedidoExistente.setProductos(nuevoPedido.getProductos());

        return pedidoRepository.save(pedidoExistente);
    }

    /**
     * Elimina un pedido.
     * @param id El identificador del pedido a eliminar.
     * @throws EntityNotFoundException Si no se encuentra el pedido con el ID especificado.
     * @throws IllegalOperationException Si el pedido tiene clientes o productos asignados.
     */
    @Override
    public void eliminar(Long idPedido) throws EntityNotFoundException, IllegalOperationException {
        Pedido pedido = pedidoRepository.findById(idPedido).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.PEDIDO_NOT_FOUND));

        if (pedido.getCliente() != null) {
            throw new IllegalOperationException("El pedido tiene clientes asignados");
        }
        if (pedido.getProductos() != null) {
            throw new IllegalOperationException("El pedido tiene productos asignados");
        }

        pedidoRepository.deleteById(idPedido);
    }

    /**
     * Asigna un cliente y un producto a un pedido existente.
     * @param idPedido El ID del pedido.
     * @param idCliente El ID del cliente a asignar.
     * @param idProducto El ID del producto a asignar.
     * @return El pedido actualizado.
     * @throws EntityNotFoundException Si no se encuentra el pedido, el cliente o el producto con el ID especificado.
     */
    @Override
    public Pedido asignarClienteProducto(Long idPedido, Long idCliente, Long idProducto) throws EntityNotFoundException, IllegalOperationException {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.PEDIDO_NOT_FOUND));

        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.CLIENTE_NOT_FOUND));

        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));

        pedido.setCliente(cliente);

        List<Producto> productos = new ArrayList<>();
        productos.add(producto);

        pedido.setProductos(productos);

        return pedidoRepository.save(pedido);
    }

    /**
     * Crea un nuevo pedido.
     * @param pedido El pedido a crear.
     * @return El pedido creado.
     * @throws IllegalOperationException Si ya existe un pedido similar en la base de datos.
     */
    @Override
    public Pedido crearPedido(Pedido pedido) throws IllegalOperationException {
        boolean pedidoExistente = pedidoRepository.existsByClienteAndProductosAndFecha(pedido.getCliente(), pedido.getProductos(), pedido.getFecha());
        
        if (pedidoExistente) {
            throw new IllegalOperationException("Ya existe un pedido similar en la base de datos.");
        }
        
        return pedidoRepository.save(pedido);
    }

    /**
     * Verifica si un estado proporcionado es válido.
     * @param estado El estado a verificar.
     * @return true si el estado es válido, false en caso contrario.
     */
    private boolean esEstadoValido(String estado) {
        List<String> estadosValidos = Arrays.asList("pendiente", "en proceso", "entregado");
        return estadosValidos.contains(estado.toLowerCase());
    }
}
