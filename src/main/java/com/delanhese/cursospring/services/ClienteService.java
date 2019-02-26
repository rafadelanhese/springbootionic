package com.delanhese.cursospring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.delanhese.cursospring.domain.Cliente;
import com.delanhese.cursospring.dto.ClienteDTO;
import com.delanhese.cursospring.repositories.ClienteRepository;
import com.delanhese.cursospring.services.exceptions.DataIntegrityException;
import com.delanhese.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);		
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente salvar(Cliente cliente) {
		cliente.setId(null);
		return clienteRepository.save(cliente);
	}
	
	public Cliente atualizar(Cliente cliente) {		
		Cliente newCliente = buscar(cliente.getId());
		updateDate(newCliente, cliente);
		return clienteRepository.save(cliente);
	}
	
	public void excluir(Integer id) {
		buscar(id);
		try {
			clienteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível porque há entidades relacionadas");
		}		
	}
	
	public List<Cliente> buscarTodos(){
		return clienteRepository.findAll();
	}
	
	public Page<Cliente> buscarPagina(Integer pagina, Integer linhasPorPagina, String ordenarPor, String direcao){
		PageRequest pageRequest = PageRequest.of(pagina,  linhasPorPagina, Direction.valueOf(direcao), ordenarPor);
		return clienteRepository.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
	}
	
	private void updateDate(Cliente newCliente, Cliente cliente) {
		newCliente.setNome(cliente.getNome());
		newCliente.setEmail(cliente.getEmail());
	}
}
