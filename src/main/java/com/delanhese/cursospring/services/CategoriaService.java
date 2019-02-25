package com.delanhese.cursospring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.delanhese.cursospring.domain.Categoria;
import com.delanhese.cursospring.dto.CategoriaDTO;
import com.delanhese.cursospring.repositories.CategoriaRepository;
import com.delanhese.cursospring.services.exceptions.DataIntegrityException;
import com.delanhese.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		System.out.print("SERVICE");
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria salvar(Categoria categoria) {
		categoria.setId(null);
		return categoriaRepository.save(categoria);
	}
	
	public Categoria atualizar(Categoria categoria) {		
		buscar(categoria.getId());
		return categoriaRepository.save(categoria);
	}
	
	public void excluir(Integer id) {
		buscar(id);
		try {
			categoriaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}		
	}
	
	public List<Categoria> buscarTodos(){
		return categoriaRepository.findAll();
	}
	
	public Page<Categoria> buscarPagina(Integer pagina, Integer linhasPorPagina, String ordenarPor, String direcao){
		PageRequest pageRequest = PageRequest.of(pagina,  linhasPorPagina, Direction.valueOf(direcao), ordenarPor);
		return categoriaRepository.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO categoriaDTO) {
		return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
	}
}
