package com.rrm14.cliente.escola.matrizcurricular.service.test;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.rrm14.cliente.escola.matrizcurricular.constants.Constantes;
import com.rrm14.cliente.escola.matrizcurricular.entity.MateriaEntity;
import com.rrm14.cliente.escola.matrizcurricular.exception.MateriaException;
import com.rrm14.cliente.escola.matrizcurricular.model.MateriaModel;
import com.rrm14.cliente.escola.matrizcurricular.repository.IMateriaRepository;
import com.rrm14.cliente.escola.matrizcurricular.service.MateriaService;



@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class MateriaServiceUnitTest {
	
	
	@Mock
	private IMateriaRepository materiaRepository;
	
	@InjectMocks
	private MateriaService materiaService;
	
	private static MateriaEntity materiaEntity;
	
	@BeforeAll
	public static void init() {
		
		materiaEntity = new MateriaEntity();
		materiaEntity.setId(1L);
		materiaEntity.setCodigo("ILP");
		materiaEntity.setFrequencia(1);
		materiaEntity.setHoras(64);
		materiaEntity.setNome("ENGENHARIA DE SOFTWARE");	
	}
	

	/*
	 * 
	 * CENARIOS DE SUCESSO
	 *
	 */
	
	
	@Test
	void testListarSucesso() {
		List<MateriaEntity> listMateria = new ArrayList<>();
		listMateria.add(materiaEntity);
		
		Mockito.when(this.materiaRepository.findAll()).thenReturn(listMateria);
		
		List<MateriaModel> listMateriaModel = this.materiaService.listar();
		
		assertNotNull(listMateriaModel);
		assertEquals("ILP", listMateriaModel.get(0).getCodigo());
		assertEquals(1, listMateriaModel.get(0).getId());
		assertEquals(1, listMateriaModel.size());
		
		Mockito.verify(this.materiaRepository, times(1)).findAll();
				
	}
	
	@Test
	void testListarHorarioMinimoSucesso() {
		List<MateriaEntity> listMateria = new ArrayList<>();
		listMateria.add(materiaEntity);
		
		Mockito.when(this.materiaRepository.findByHoraMinima(64)).thenReturn(listMateria);
		
		List<MateriaModel> listMateriaModel = this.materiaService.listarPorHorarioMinimo(64);
		
		assertNotNull(listMateriaModel);
		assertEquals("ILP", listMateriaModel.get(0).getCodigo());
		assertEquals(1, listMateriaModel.get(0).getId());
		assertEquals(1, listMateriaModel.size());
		
		Mockito.verify(this.materiaRepository, times(1)).findByHoraMinima(64);
				
	}
	
	@Test
	void testListarPorFrequenciaSucesso() {
		List<MateriaEntity> listMateria = new ArrayList<>();
		listMateria.add(materiaEntity);
		
		Mockito.when(this.materiaRepository.findByFrequencia(1)).thenReturn(listMateria);
		
		List<MateriaModel> listMateriaModel = this.materiaService.listarPorFrequencia(1);
		
		assertNotNull(listMateriaModel);
		assertEquals("ILP", listMateriaModel.get(0).getCodigo());
		assertEquals(1, listMateriaModel.get(0).getId());
		assertEquals(1, listMateriaModel.size());
		
		Mockito.verify(this.materiaRepository, times(1)).findByFrequencia(1);
				
	}
	
	@Test
	void testConsultarMateriaSucesso() {
		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
		
		MateriaModel materiaModel = this.materiaService.consultar(1L);
		assertNotNull(materiaModel);
		assertEquals("ILP", materiaModel.getCodigo());
		assertEquals(1, materiaModel.getId());
		assertEquals(1, materiaModel.getFrequencia());
		
		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
				
	}
	
	@Test
	void testCadastrarMateriaSucesso() {
		
		MateriaModel materiaModel = new MateriaModel();
		materiaModel.setCodigo("ILP");
		materiaModel.setFrequencia(1);
		materiaModel.setHoras(64);
		materiaModel.setNome("ENGENHARIA DE SOFTWARE");
		
		materiaEntity.setId(null);
		
		Mockito.when(this.materiaRepository.findMateriaByCodigo("ILP")).thenReturn(null);
		Mockito.when(this.materiaRepository.save(materiaEntity)).thenReturn(materiaEntity);
		
		Boolean sucesso = this.materiaService.cadastrar(materiaModel);
		
		assertTrue(sucesso);
		Mockito.verify(this.materiaRepository, times(1)).findMateriaByCodigo("ILP");
		Mockito.verify(this.materiaRepository, times(1)).save(materiaEntity);
		
		materiaEntity.setId(1L);
					
	}
	
	@Test
	void testAtualizarMateriaSucesso() {
		
		MateriaModel materiaModel = new MateriaModel();
		materiaModel.setId(1L);
		materiaModel.setCodigo("ILP");
		materiaModel.setFrequencia(1);
		materiaModel.setHoras(64);
		materiaModel.setNome("ENGENHARIA DE SOFTWARE");
		
		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
		Mockito.when(this.materiaRepository.save(materiaEntity)).thenReturn(materiaEntity);
		
		Boolean sucesso = this.materiaService.atualizar(materiaModel);
		
		assertTrue(sucesso);
		Mockito.verify(this.materiaRepository, times(0)).findMateriaByCodigo("ILP");
		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
		Mockito.verify(this.materiaRepository, times(1)).save(materiaEntity);
		
	}
	
	@Test
	void testExcluirMateriaSucesso() {
		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
		
		Boolean sucesso = this.materiaService.excluir(1L);
		
		assertTrue(sucesso);
		Mockito.verify(this.materiaRepository, times(0)).findMateriaByCodigo("ILP");
		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
		Mockito.verify(this.materiaRepository, times(1)).deleteById(1L);
				
	}
	
	/*
	 * 
	 * CENARIOS DE THROW-MATERIA-EXCEPTION
	 *
	 */
	
	@Test
	void testAtualizarThrowMateriaException() {
		
		MateriaModel materiaModel = new MateriaModel();
		materiaModel.setId(1L);
		materiaModel.setCodigo("ILP");
		materiaModel.setFrequencia(1);
		materiaModel.setHoras(64);
		materiaModel.setNome("ENGENHARIA DE SOFTWARE");
		
		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.empty());
		
		MateriaException materiaException;
		
		materiaException = assertThrows(MateriaException.class, ()->{
			this.materiaService.atualizar(materiaModel);
		});
		
		assertEquals(HttpStatus.NOT_FOUND, materiaException.getHttpStatus());
		assertEquals(Constantes.MATERIA_NAO_ENCONTRADA, materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
		Mockito.verify(this.materiaRepository, times(0)).save(materiaEntity);
		
	}
	
	@Test
	void testExcluirThrowMateriaException() {
		
		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.empty());
		
		MateriaException materiaException;
		
		materiaException = assertThrows(MateriaException.class, ()->{
			this.materiaService.excluir(1L);
		});
		
		assertEquals(HttpStatus.NOT_FOUND, materiaException.getHttpStatus());
		assertEquals(Constantes.MATERIA_NAO_ENCONTRADA, materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
		Mockito.verify(this.materiaRepository, times(0)).deleteById(1L);
		
	}
	
	@Test
	void testCadastrarComIdThrowMateriaException() {
		
		MateriaModel materiaModel = new MateriaModel();
		materiaModel.setId(1L);
		materiaModel.setCodigo("ILP");
		materiaModel.setFrequencia(1);
		materiaModel.setHoras(64);
		materiaModel.setNome("ENGENHARIA DE SOFTWARE");

		MateriaException materiaException;
		
		materiaException = assertThrows(MateriaException.class, ()->{
			this.materiaService.cadastrar(materiaModel);
		});
		
		assertEquals(HttpStatus.BAD_REQUEST, materiaException.getHttpStatus());
		assertEquals(Constantes.ERRO_ID_INFORMADO, materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(0)).findMateriaByCodigo("ILP");
		Mockito.verify(this.materiaRepository, times(0)).save(materiaEntity);
		
	}
	
	@Test
	void testCadastrarComCodigoExistenteThrowMateriaException() {
		
		MateriaModel materiaModel = new MateriaModel();
		materiaModel.setCodigo("ILP");
		materiaModel.setFrequencia(1);
		materiaModel.setHoras(64);
		materiaModel.setNome("ENGENHARIA DE SOFTWARE");
		
		Mockito.when(this.materiaRepository.findMateriaByCodigo("ILP")).thenReturn(materiaEntity);

		MateriaException materiaException;
		
		materiaException = assertThrows(MateriaException.class, ()->{
			this.materiaService.cadastrar(materiaModel);
		});
		
		assertEquals(HttpStatus.BAD_REQUEST, materiaException.getHttpStatus());
		assertEquals(Constantes.MATERIA_JA_CADASTRADA, materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).findMateriaByCodigo("ILP");
		Mockito.verify(this.materiaRepository, times(0)).save(materiaEntity);
		
	}
	
	/*
	 * 
	 * CENARIOS DE THROW-EXCEPTION
	 *
	 */
	
	@Test
	void testAtualizarThrowException() {
		
		MateriaModel materiaModel = new MateriaModel();
		materiaModel.setId(1L);
		materiaModel.setCodigo("ILP");
		materiaModel.setFrequencia(1);
		materiaModel.setHoras(64);
		materiaModel.setNome("ENGENHARIA DE SOFTWARE");
		
		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
		Mockito.when(this.materiaRepository.save(materiaEntity)).thenThrow(IllegalStateException.class);
		
		MateriaException materiaException;
		
		materiaException = assertThrows(MateriaException.class, ()->{
			this.materiaService.atualizar(materiaModel);
		});
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
		assertEquals(Constantes.ERRO_INTERNO, materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
		Mockito.verify(this.materiaRepository, times(1)).save(materiaEntity);
		
	}
	
	@Test
	void testListarThrowException() {
		Mockito.when(this.materiaRepository.findAll()).thenThrow(IllegalStateException.class);
		
		MateriaException materiaException;
		
		materiaException = assertThrows(MateriaException.class, ()->{
			this.materiaService.listar();
		});
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
		assertEquals(Constantes.ERRO_INTERNO, materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).findAll();
	}
	
	@Test
	void testListarHorarioMinimoThrowException() {
		Mockito.when(this.materiaRepository.findByHoraMinima(64)).thenThrow(IllegalStateException.class);
		
		MateriaException materiaException;
		
		materiaException = assertThrows(MateriaException.class, ()->{
			this.materiaService.listarPorHorarioMinimo(64);
		});
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
		assertEquals(Constantes.ERRO_INTERNO, materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).findByHoraMinima(64);
				
	}
	
	@Test
	void testListarPorFrequenciaThrowException() {
		Mockito.when(this.materiaRepository.findByFrequencia(1)).thenThrow(IllegalStateException.class);
		
		MateriaException materiaException;
		
		materiaException = assertThrows(MateriaException.class, ()->{
			this.materiaService.listarPorFrequencia(1);
		});
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
		assertEquals(Constantes.ERRO_INTERNO, materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).findByFrequencia(1);
				
	}
	
	@Test
	void testCadastrarThrowException() {
		
		MateriaModel materiaModel = new MateriaModel();
		materiaModel.setCodigo("ILP");
		materiaModel.setFrequencia(1);
		materiaModel.setHoras(64);
		materiaModel.setNome("ENGENHARIA DE SOFTWARE");
		
		materiaEntity.setId(null);
		
		Mockito.when(this.materiaRepository.findMateriaByCodigo("ILP")).thenReturn(null);
		Mockito.when(this.materiaRepository.save(materiaEntity)).thenThrow(IllegalStateException.class);

		MateriaException materiaException;
		
		materiaException = assertThrows(MateriaException.class, ()->{
			this.materiaService.cadastrar(materiaModel);
		});
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
		assertEquals(Constantes.ERRO_INTERNO, materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).findMateriaByCodigo("ILP");
		Mockito.verify(this.materiaRepository, times(1)).save(materiaEntity);
		
		materiaEntity.setId(1L);
		
	}
	
	@Test
	void testConsultarMateriaThrowException() {
		Mockito.when(this.materiaRepository.findById(1L)).thenThrow(IllegalStateException.class);
		
		MateriaException materiaException;
		
		materiaException = assertThrows(MateriaException.class, ()->{
			this.materiaService.consultar(1L);
		});
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
		assertEquals(Constantes.ERRO_INTERNO, materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
				
	}
	
	@Test
	void testExcluirThrowException() {
		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
		Mockito.doThrow(IllegalStateException.class).when(this.materiaRepository).deleteById(1L);
		
		MateriaException materiaException;
		
		materiaException = assertThrows(MateriaException.class, ()->{
			this.materiaService.excluir(1L);
		});
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
		assertEquals(Constantes.ERRO_INTERNO, materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
		Mockito.verify(this.materiaRepository, times(1)).deleteById(1L);
				
	}

}
