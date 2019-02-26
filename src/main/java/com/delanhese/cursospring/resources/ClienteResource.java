package com.delanhese.cursospring.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.delanhese.cursospring.domain.Cliente;
import com.delanhese.cursospring.dto.ClienteDTO;
import com.delanhese.cursospring.dto.ClienteNewDTO;
import com.delanhese.cursospring.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("/{id}")	
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {			
		Cliente cliente = clienteService.buscar(id);		
		return ResponseEntity.ok(cliente);		
	}	
	
	@PostMapping
	public ResponseEntity<Void> inserir(@Valid @RequestBody ClienteNewDTO clienteNewDTO){
		Cliente cliente = clienteService.fromDTO(clienteNewDTO);
		cliente = clienteService.salvar(cliente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(cliente.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizar(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Integer id){
		Cliente cliente = clienteService.fromDTO(clienteDTO);
		cliente.setId(id);
		cliente = clienteService.atualizar(cliente);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")	
	public ResponseEntity<Void> excluir(@PathVariable Integer id) {			
		clienteService.excluir(id);		
		return ResponseEntity.noContent().build();		
	}	
	
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> buscarTodos() {			
		List<Cliente> listaCliente = clienteService.buscarTodos();
		List<ClienteDTO> listaClienteDTO = listaCliente.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaClienteDTO);		
	}	
	
	@GetMapping("/pagina")
	public ResponseEntity<Page<ClienteDTO>> buscarPagina(
			@RequestParam(value="pagina", defaultValue="0")Integer pagina, 
			@RequestParam(value="linhasPorPagina", defaultValue="24")Integer linhasPorPagina, 
			@RequestParam(value="ordenarPor", defaultValue="nome")String ordenarPor, 
			@RequestParam(value="direcao", defaultValue="ASC")String direcao) {			
		Page<Cliente> listaCliente = clienteService.buscarPagina(pagina, linhasPorPagina, ordenarPor, direcao);
		Page<ClienteDTO> listaClienteDTO = listaCliente.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listaClienteDTO);		
	}	
		
}
