package com.delanhese.cursospring.dto;

import java.io.Serializable;

import com.delanhese.cursospring.domain.Categoria;

public class CategoriaDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	
	public CategoriaDTO() {		
	}

	public CategoriaDTO(Categoria categoria) {
		this.id = categoria.getId();
		this.nome = categoria.getNome();
	}
	
	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
		
}
