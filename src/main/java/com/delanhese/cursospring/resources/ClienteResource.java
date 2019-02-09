package com.delanhese.cursospring.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.delanhese.cursospring.domain.Cliente;
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
		
}
