package com.rrm14.cliente.escola.matrizcurricular.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.rrm14.cliente.escola.matrizcurricular.constants.Constantes;
import com.rrm14.cliente.escola.matrizcurricular.entity.CursoEntity;
import com.rrm14.cliente.escola.matrizcurricular.entity.MateriaEntity;
import com.rrm14.cliente.escola.matrizcurricular.exception.CursoException;
import com.rrm14.cliente.escola.matrizcurricular.model.CursoModel;
import com.rrm14.cliente.escola.matrizcurricular.repository.ICursoRepository;
import com.rrm14.cliente.escola.matrizcurricular.repository.IMateriaRepository;


@CacheConfig(cacheNames = {"curso"})
@Service
public class CursoService implements ICursoService {
	
	private ICursoRepository cursoRepository;
	private IMateriaRepository materiaRepository;

	@Autowired
	public CursoService(ICursoRepository cursoRepository, 
						IMateriaRepository materiaRepository) {
		this.cursoRepository = cursoRepository;
		this.materiaRepository = materiaRepository;
	}
	
	@CachePut(key = "#codigo")
	@Override
	public CursoEntity consultarPorCodigo(String codigo) {
		try {
			CursoEntity curso = this.cursoRepository.findCursoByCodigo(codigo);
			
			if(curso == null) {		
				throw new CursoException(Constantes.CURSO_NAO_ENCONTRADO, HttpStatus.NOT_FOUND);
			}
			
			return curso;
			
		}catch(CursoException c) {
			throw c;
		}catch(Exception e) {
			throw new CursoException(Constantes.ERRO_INTERNO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CachePut(unless = "#result.size()<3")
	@Override
	public List<CursoEntity> listar() {
		try {
			return this.cursoRepository.findAll();
		}catch(Exception e) {
			throw new CursoException(Constantes.ERRO_INTERNO, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@Override
	public Boolean cadastrar(CursoModel cursoModel) {
		try {		
			
			// Valida se ID Foi Informado
			
			if (cursoModel.getId() != null) {
				throw new CursoException(Constantes.ERRO_ID_INFORMADO, HttpStatus.BAD_REQUEST);
			}
			
			// Não Permite Fazer Cadastro De Cursos Já Cadastrados 
			
			if (this.cursoRepository.findCursoByCodigo(cursoModel.getCodigo())!= null) {
				throw new CursoException(Constantes.CURSO_JA_CADASTRADO, HttpStatus.BAD_REQUEST);
			}
						
			return this.cadastrarOuAtualizar(cursoModel);
		}catch(CursoException c) {
			throw c;
		}catch(Exception e) {
			throw new CursoException(Constantes.ERRO_INTERNO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean atualizar(CursoModel cursoModel) {
		try {
			this.consultarPorCodigo(cursoModel.getCodigo());
			return cadastrarOuAtualizar(cursoModel);
		}catch(CursoException c) {
			throw c;
		}catch(Exception e) {
			throw new CursoException(Constantes.ERRO_INTERNO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean excluir(Long id) {
		try {
			if (this.cursoRepository.findById(id).isPresent()) {
				this.cursoRepository.deleteById(id);
				return Boolean.TRUE;
			}
			throw new CursoException(Constantes.CURSO_NAO_ENCONTRADO, HttpStatus.NOT_FOUND);
			
		}catch(CursoException c) {
			throw c;
		}catch(Exception e) {
			throw new CursoException(Constantes.ERRO_INTERNO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private Boolean cadastrarOuAtualizar(CursoModel cursoModel) {
		
		List<MateriaEntity> listaMateriaEntity = new ArrayList<>();
		
		if(cursoModel.getMaterias()!= null && !cursoModel.getMaterias().isEmpty()) {
			cursoModel.getMaterias().forEach(materia->{
				if (this.materiaRepository.findById(materia).isPresent()) {
					listaMateriaEntity.add(this.materiaRepository.findById(materia).get());
				}
			});
		}
		
		CursoEntity cursoEntity = new CursoEntity();
		
		if(cursoModel.getId() != null) {
			cursoEntity.setId(cursoModel.getId());
		}
		
		cursoEntity.setCodigo(cursoModel.getCodigo());
		cursoEntity.setNome(cursoModel.getNome());
		cursoEntity.setMaterias(listaMateriaEntity);
			
		this.cursoRepository.save(cursoEntity);
		
		return Boolean.TRUE;
		
	}	

}
