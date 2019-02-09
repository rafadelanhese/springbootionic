package com.delanhese.cursospring.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.delanhese.cursospring.domain.Pedido;
import com.delanhese.cursospring.services.PedidoService;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService pedidoService;
	
	@GetMapping("/{id}")	
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {			
		Pedido pedido = pedidoService.buscar(id);		
		return ResponseEntity.ok(pedido);		
	}	
		
}
