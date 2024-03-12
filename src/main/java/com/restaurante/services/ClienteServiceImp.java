package com.restaurante.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.restaurante.domain.Cliente;
import com.restaurante.repositories.ClienteRepository;
import com.restaurante.exception.EntityNotFoundException;
import com.restaurante.exception.ErrorMessage;
import com.restaurante.exception.IllegalOperationException;

/**
 * Implementación del servicio para la entidad Cliente.
 */
@Service
public class ClienteServiceImp implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Obtiene una lista de todos los clientes.
     * @return Una lista de clientes.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    /**
     * Busca un cliente por su identificador.
     * @param id El identificador del cliente a buscar.
     * @return El cliente encontrado.
     * @throws EntityNotFoundException Si no se encuentra el cliente con el ID especificado.
     */
    @Override
    @Transactional(readOnly = true)
    public Cliente buscarPorId(Long id) throws EntityNotFoundException {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        return clienteOptional.orElseThrow(() -> new EntityNotFoundException(ErrorMessage.CLIENTE_NOT_FOUND));
    }

    /**
     * Guarda un nuevo cliente.
     * @param cliente El cliente a guardar.
     * @return El cliente guardado.
     * @throws IllegalOperationException Si ocurre una operación ilegal al intentar guardar el cliente.
     */
    @Override
    @Transactional
    public Cliente grabar(Cliente cliente) throws IllegalOperationException {
    	if(!clienteRepository.findByNombre(cliente.getNombre()).isEmpty()) {
			throw new IllegalOperationException("El nombre del departamento ya existe");
		}
		return clienteRepository.save(cliente);
    }

    /**
     * Actualiza un cliente existente.
     * @param id El identificador del cliente a actualizar.
     * @param cliente El cliente con los datos actualizados.
     * @return El cliente actualizado.
     * @throws EntityNotFoundException Si no se encuentra el cliente con el ID especificado.
     * @throws IllegalOperationException Si ocurre una operación ilegal al intentar actualizar el cliente.
     */
    @Override
    @Transactional
    public Cliente actualizar(Long id, Cliente cliente) throws EntityNotFoundException, IllegalOperationException {
    	Optional<Cliente> clienteEntity = clienteRepository.findById(id);
		if(clienteEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.CLIENTE_NOT_FOUND);
		if(!clienteRepository.findByNombre(cliente.getNombre()).isEmpty()) {
			throw new IllegalOperationException("El nombre del cliente ya existe");
		}	
		cliente.setId(id);		
		return clienteRepository.save(cliente);
	}
    

    /**
     * Elimina un cliente.
     * @param id El identificador del cliente a eliminar.
     * @throws EntityNotFoundException Si no se encuentra el cliente con el ID especificado.
     * @throws IllegalOperationException Si ocurre una operación ilegal al intentar eliminar el cliente.
     */
    @Override
    @Transactional
    public void eliminar(Long idCliente) throws EntityNotFoundException, IllegalOperationException {
    	Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
				()->new EntityNotFoundException(ErrorMessage.CLIENTE_NOT_FOUND)
				);
    	if (!(cliente.getPedidos().isEmpty())) {
			throw new IllegalOperationException("El cliente tiene pedidos asignados");
		}
	
						
		clienteRepository.deleteById(idCliente);
    }
    @Override
    public List<Cliente> buscarPorNombre(String nombre) {
        return clienteRepository.findByNombre(nombre);
    }
}
