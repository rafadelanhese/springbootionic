package com.delanhese.cursospring.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.delanhese.cursospring.domain.Categoria;
import com.delanhese.cursospring.dto.CategoriaDTO;
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
	
	@PostMapping
	public ResponseEntity<Void> inserir(@RequestBody Categoria categoria){
		categoria = categoriaService.salvar(categoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(categoria.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizar(@RequestBody Categoria categoria, @PathVariable Integer id){
		categoria.setId(id);
		categoria = categoriaService.atualizar(categoria);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")	
	public ResponseEntity<Void> excluir(@PathVariable Integer id) {			
		categoriaService.excluir(id);		
		return ResponseEntity.noContent().build();		
	}	
	
	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> buscarTodos() {			
		List<Categoria> listaCategoria = categoriaService.buscarTodos();
		List<CategoriaDTO> listaCategoriaDTO = listaCategoria.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaCategoriaDTO);		
	}	
}
