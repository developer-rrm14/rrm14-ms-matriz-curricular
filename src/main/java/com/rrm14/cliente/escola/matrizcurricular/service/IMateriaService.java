package com.rrm14.cliente.escola.matrizcurricular.service;

import java.util.List;

import com.rrm14.cliente.escola.matrizcurricular.model.MateriaModel;

public interface IMateriaService {
	
	public MateriaModel consultar(final Long id);
	
	public List<MateriaModel> listar();
	
	public Boolean cadastrar(final MateriaModel materia);
	
	public Boolean atualizar(final MateriaModel materia);
	
	public Boolean excluir(final Long id);
	
	public List<MateriaModel> listarPorHorarioMinimo(int horaMinima);
	
	public List<MateriaModel> listarPorFrequencia(int frequencia);

}
