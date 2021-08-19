package com.rrm14.cliente.escola.matrizcurricular.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.rrm14.cliente.escola.matrizcurricular.constantes.Constantes;
import com.rrm14.cliente.escola.matrizcurricular.entity.MateriaEntity;
import com.rrm14.cliente.escola.matrizcurricular.exception.MateriaException;
import com.rrm14.cliente.escola.matrizcurricular.model.MateriaModel;
import com.rrm14.cliente.escola.matrizcurricular.repository.IMateriaRepository;

@CacheConfig(cacheNames = {"materia"})
@Service
public class MateriaService implements IMateriaService {
	
	private IMateriaRepository materiaRepository;
	private ModelMapper mapper;
	
	@Autowired
	public MateriaService(IMateriaRepository materiaRepository) {
		this.mapper = new ModelMapper();
		this.materiaRepository = materiaRepository;
	}
	
	@CachePut(key = "#id")
	@Override
	public MateriaModel consultar(Long id) {
		try {
			Optional<MateriaEntity> materiaOptional = this.materiaRepository.findById(id);
			if(materiaOptional.isPresent()) {		
				return this.mapper.map(materiaOptional.get(), MateriaModel.class);
			}
			throw new MateriaException(Constantes.MATERIA_NAO_ENCONTRADA, HttpStatus.NOT_FOUND);
		}catch(MateriaException m) {
			throw m;
		}catch(Exception e) {
			throw new MateriaException(Constantes.ERRO_INTERNO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CachePut(unless = "#result.size()<3")
	@Override
	public List<MateriaModel> listar() {
		try {
			return this.mapper.map(this.materiaRepository.findAll(), 
					new TypeToken<List<MateriaModel>>(){}.getType());
		}catch(Exception e) {
			return new ArrayList<>();
		}	
	}
	
	@Override
	public Boolean cadastrar(MateriaModel materiaModel) {
		try {		
			// Parse do MateriaModel para MateriaEntity
			MateriaEntity materiaEntity = this.mapper.map(materiaModel, MateriaEntity.class);		
			this.materiaRepository.save(materiaEntity);
			return Boolean.TRUE;
		}catch(Exception e) {
			throw new MateriaException(Constantes.ERRO_INTERNO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean atualizar(MateriaModel materiaModel) {
		try {
			this.consultar(materiaModel.getId());
		
			// Parse do MateriaModel para MateriaEntity
			MateriaEntity materiaAtualiza = this.mapper.map(materiaModel, MateriaEntity.class);
			this.materiaRepository.save(materiaAtualiza);
			
			return Boolean.TRUE;
		}catch(MateriaException m) {
			throw m;
		}catch(Exception e) {
			throw new MateriaException(Constantes.ERRO_INTERNO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean excluir(Long id) {
		try {
			this.consultar(id);
			this.materiaRepository.deleteById(id);
			return Boolean.TRUE;
		}catch(MateriaException m) {
			throw m;
		}catch(Exception e) {
			if (e.getMessage().contains("ConstraintViolationException")) {
				throw new MateriaException(Constantes.MATERIA_VIOLACAO_REMOCAO, HttpStatus.INTERNAL_SERVER_ERROR);
			}else {
				throw new MateriaException(Constantes.ERRO_INTERNO, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
	}
	
	@CachePut(unless = "#result.size()<3")
	@Override
	public List<MateriaModel> listarPorHorarioMinimo(int horaMinima) {
		try {
			return this.mapper.map(this.materiaRepository.findByHoraMinima(horaMinima), 
					new TypeToken<List<MateriaModel>>(){}.getType());
		}catch(Exception e) {
			return new ArrayList<>();
		}	
	}
	
	@CachePut(unless = "#result.size()<3")
	@Override
	public List<MateriaModel> listarPorFrequencia(int frequencia) {
		try {
			return this.mapper.map(this.materiaRepository.findByFrequencia(frequencia), 
					new TypeToken<List<MateriaModel>>(){}.getType());
		}catch(Exception e) {
			return new ArrayList<>();
		}	
	}

	

}
