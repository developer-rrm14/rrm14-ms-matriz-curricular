package com.rrm14.cliente.escola.matrizcurricular.service;

import java.util.List;

import com.rrm14.cliente.escola.matrizcurricular.entity.CursoEntity;
import com.rrm14.cliente.escola.matrizcurricular.model.CursoModel;

public interface ICursoService {
	
	public CursoEntity consultarPorCodigo(final String codigo);
	
	public List<CursoEntity> listar();
	
	public Boolean cadastrar(final CursoModel curso);
	
	public Boolean atualizar(final CursoModel curso);
	
	public Boolean excluir(final Long id);
	
}
