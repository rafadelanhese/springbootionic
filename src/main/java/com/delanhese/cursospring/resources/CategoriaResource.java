package com.delanhese.cursospring.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.delanhese.cursospring.domain.Categoria;
import com.delanhese.cursospring.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping("/{id}")	
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {			
		Categoria categoria = categoriaService.buscar(id);		
		return ResponseEntity.ok(categoria);		
	}	
		
}
