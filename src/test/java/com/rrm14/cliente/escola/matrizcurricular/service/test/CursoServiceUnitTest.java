package com.rrm14.cliente.escola.matrizcurricular.service.test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import com.rrm14.cliente.escola.matrizcurricular.entity.CursoEntity;
import com.rrm14.cliente.escola.matrizcurricular.entity.MateriaEntity;
import com.rrm14.cliente.escola.matrizcurricular.exception.CursoException;
import com.rrm14.cliente.escola.matrizcurricular.model.CursoModel;
import com.rrm14.cliente.escola.matrizcurricular.model.MateriaModel;
import com.rrm14.cliente.escola.matrizcurricular.repository.ICursoRepository;
import com.rrm14.cliente.escola.matrizcurricular.repository.IMateriaRepository;
import com.rrm14.cliente.escola.matrizcurricular.service.CursoService;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class CursoServiceUnitTest {
	
	
	@Mock
	private ICursoRepository cursoRepository;
	
	@Mock
	private IMateriaRepository materiaRepository;
	
	@InjectMocks
	private CursoService cursoService;
	
	private static CursoEntity cursoEntity;
	
	private static MateriaEntity materiaEntity;
	
	@BeforeAll
	public static void init() {
		
		materiaEntity = new MateriaEntity();
		materiaEntity.setId(1L);
		materiaEntity.setCodigo("ILP");
		materiaEntity.setFrequencia(1);
		materiaEntity.setHoras(64);
		materiaEntity.setNome("ENGENHARIA DE SOFTWARE");	
		
		List<MateriaEntity> listaMateria = new ArrayList<>();
		
		listaMateria.add(materiaEntity);
		
		cursoEntity = new CursoEntity();
		cursoEntity.setId(1L);
		cursoEntity.setCodigo("ENGECI");
		cursoEntity.setNome("ENGENHARIA CIVIL");
		cursoEntity.setMaterias(listaMateria);
		
	}
	

	/*
	 * 
	 * CENARIOS DE SUCESSO
	 *
	 */
	
	
	@Test
	void testListarSucesso() {
		List<CursoEntity> listCurso = new ArrayList<>();
		listCurso.add(cursoEntity);
		
		Mockito.when(this.cursoRepository.findAll()).thenReturn(listCurso);
		
		List<CursoEntity> listCursoEntity = this.cursoService.listar();
		
		assertNotNull(listCursoEntity);
		assertEquals("ENGECI", listCursoEntity.get(0).getCodigo());	
		assertEquals(1, listCursoEntity.size());
		
		Mockito.verify(this.cursoRepository, times(1)).findAll();
				
	}
	

	@Test
	void testConsultarCursoPorCodigoSucesso() {
		Mockito.when(this.cursoRepository.findCursoByCodigo("ENGECI")).thenReturn(cursoEntity);
		
		CursoEntity cursoEntity = this.cursoService.consultarPorCodigo("ENGECI");
		assertNotNull(cursoEntity);
		assertEquals("ENGECI", cursoEntity.getCodigo());
		
		Mockito.verify(this.cursoRepository, times(1)).findCursoByCodigo("ENGECI");
				
	}
	
	@Test
	void testCadastrarCursoSucesso() {
		
		MateriaModel materiaModel = new MateriaModel();
		materiaModel.setCodigo("ILP");
		materiaModel.setFrequencia(1);
		materiaModel.setHoras(64);
		materiaModel.setNome("ENGENHARIA DE SOFTWARE");
		materiaModel.setId(1L);
		
		List<Long> listaMateria = new ArrayList<>();
		
		listaMateria.add(materiaModel.getId());
		
		CursoModel cursoModel = new CursoModel();
		cursoModel.setCodigo("ENGECI");
		cursoModel.setNome("ENGENHARIA CIVIL");
		cursoModel.setMaterias(listaMateria);
		cursoEntity.setId(null);
		
		Mockito.when(this.cursoRepository.findCursoByCodigo("ENGECI")).thenReturn(null);
		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
		Mockito.when(this.cursoRepository.save(cursoEntity)).thenReturn(cursoEntity);
		
		Boolean sucesso = this.cursoService.cadastrar(cursoModel);
		
		assertTrue(sucesso);
		Mockito.verify(this.cursoRepository, times(1)).findCursoByCodigo("ENGECI");
		Mockito.verify(this.materiaRepository, times(2)).findById(1L);
 		Mockito.verify(this.cursoRepository, times(1)).save(cursoEntity);
		
		cursoEntity.setId(1L);
					
	}
	
	@Test
	void testAtualizarCursoSucesso() {
		
		MateriaModel materiaModel = new MateriaModel();
		materiaModel.setId(1L);
		materiaModel.setCodigo("ILP");
		materiaModel.setFrequencia(1);
		materiaModel.setHoras(64);
		materiaModel.setNome("ENGENHARIA DE SOFTWARE");
		
		List<Long> listaMateria = new ArrayList<>();
		
		listaMateria.add(materiaModel.getId());
		
		CursoModel cursoModel = new CursoModel();
		cursoModel.setCodigo("ENGECI");
		cursoModel.setNome("ENGENHARIA CIVIL");
		cursoModel.setMaterias(listaMateria);
		cursoEntity.setId(null);
		
		Mockito.when(this.cursoRepository.findCursoByCodigo("ENGECI")).thenReturn(cursoEntity);
		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
		Mockito.when(this.cursoRepository.save(cursoEntity)).thenReturn(cursoEntity);
		
		Boolean sucesso = this.cursoService.atualizar(cursoModel);
		
		assertTrue(sucesso);
		Mockito.verify(this.cursoRepository, times(1)).findCursoByCodigo("ENGECI");
		Mockito.verify(this.materiaRepository, times(2)).findById(1L);
 		Mockito.verify(this.cursoRepository, times(1)).save(cursoEntity);
		
	}
	
	@Test
	void testExcluirCursoSucesso() {
		Mockito.when(this.cursoRepository.findById(1L)).thenReturn(Optional.of(cursoEntity));
		
		Boolean sucesso = this.cursoService.excluir(1L);
		
		assertTrue(sucesso);
		
		Mockito.verify(this.cursoRepository, times(1)).findById(1L);
		Mockito.verify(this.cursoRepository, times(1)).deleteById(1L);
				
	}
	
	/*
	 * 
	 * CENARIOS DE THROW-MATERIA-EXCEPTION
	 *
	 */
	
	@Test
	void testAtualizarThrowCursoException() {
		
		MateriaModel materiaModel = new MateriaModel();
		materiaModel.setId(1L);
		materiaModel.setCodigo("ILP");
		materiaModel.setFrequencia(1);
		materiaModel.setHoras(64);
		materiaModel.setNome("ENGENHARIA DE SOFTWARE");
		
		List<Long> listaMateria = new ArrayList<>();
		
		listaMateria.add(materiaModel.getId());
		
		CursoModel cursoModel = new CursoModel();
		cursoModel.setId(1L);
		cursoModel.setCodigo("ENGECI");
		cursoModel.setNome("ENGENHARIA CIVIL");
		cursoModel.setMaterias(listaMateria);
		
		Mockito.when(this.cursoRepository.findCursoByCodigo("ENGECI")).thenReturn(null);
		
		CursoException cursoException;
		
		cursoException = assertThrows(CursoException.class, ()->{
			this.cursoService.atualizar(cursoModel);
		});
		
		assertEquals(HttpStatus.NOT_FOUND, cursoException.getHttpStatus());
		assertEquals(Constantes.CURSO_NAO_ENCONTRADO, cursoException.getMessage());
		
		Mockito.verify(this.cursoRepository, times(1)).findCursoByCodigo("ENGECI");
		Mockito.verify(this.cursoRepository, times(0)).save(cursoEntity);
		
	}
	
	@Test
	void testExcluirThrowCursoException() {
		
		Mockito.when(this.cursoRepository.findById(1L)).thenReturn(Optional.empty());
		
		CursoException cursoException;
		
		cursoException = assertThrows(CursoException.class, ()->{
			this.cursoService.excluir(1L);
		});
		
		assertEquals(HttpStatus.NOT_FOUND, cursoException.getHttpStatus());
		assertEquals(Constantes.CURSO_NAO_ENCONTRADO, cursoException.getMessage());
		
		Mockito.verify(this.cursoRepository, times(1)).findById(1L);
		Mockito.verify(this.cursoRepository, times(0)).deleteById(1L);
		
	}
	
	@Test
	void testCadastrarComIdThrowCursoException() {
		
		MateriaModel materiaModel = new MateriaModel();
		materiaModel.setId(1L);
		materiaModel.setCodigo("ILP");
		materiaModel.setFrequencia(1);
		materiaModel.setHoras(64);
		materiaModel.setNome("ENGENHARIA DE SOFTWARE");
		
		List<Long> listaMateria = new ArrayList<>();
		
		listaMateria.add(materiaModel.getId());
		
		CursoModel cursoModel = new CursoModel();
		cursoModel.setId(1L);
		cursoModel.setCodigo("ENGECI");
		cursoModel.setNome("ENGENHARIA CIVIL");
		cursoModel.setMaterias(listaMateria);

		CursoException cursoException;
		
		cursoException = assertThrows(CursoException.class, ()->{
			this.cursoService.cadastrar(cursoModel);
		});
		
		assertEquals(HttpStatus.BAD_REQUEST, cursoException.getHttpStatus());
		assertEquals(Constantes.ERRO_ID_INFORMADO, cursoException.getMessage());
		
		Mockito.verify(this.cursoRepository, times(0)).save(cursoEntity);
		
	}
	
	@Test
	void testCadastrarComCodigoExistenteThrowCursoException() {
		
		MateriaModel materiaModel = new MateriaModel();
		materiaModel.setCodigo("ILP");
		materiaModel.setFrequencia(1);
		materiaModel.setHoras(64);
		materiaModel.setNome("ENGENHARIA DE SOFTWARE");
		
		List<Long> listaMateria = new ArrayList<>();
		
		listaMateria.add(materiaModel.getId());
		
		CursoModel cursoModel = new CursoModel();
		cursoModel.setCodigo("ENGECI");
		cursoModel.setNome("ENGENHARIA CIVIL");
		cursoModel.setMaterias(listaMateria);
		
		Mockito.when(this.cursoRepository.findCursoByCodigo("ENGECI")).thenReturn(cursoEntity);

		CursoException cursoException;
		
		cursoException = assertThrows(CursoException.class, ()->{
			this.cursoService.cadastrar(cursoModel);
		});
		
		
		assertEquals(HttpStatus.BAD_REQUEST, cursoException.getHttpStatus());
		assertEquals(Constantes.CURSO_JA_CADASTRADO, cursoException.getMessage());
		
		Mockito.verify(this.cursoRepository, times(1)).findCursoByCodigo("ENGECI");
		Mockito.verify(this.cursoRepository, times(0)).save(cursoEntity);
		
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
		
		List<Long> listaMateria = new ArrayList<>();
		
		listaMateria.add(materiaModel.getId());
		
		CursoModel cursoModel = new CursoModel();
		cursoModel.setId(1L);
		cursoModel.setCodigo("ENGECI");
		cursoModel.setNome("ENGENHARIA CIVIL");
		cursoModel.setMaterias(listaMateria);
		
		Mockito.when(this.cursoRepository.findCursoByCodigo("ENGECI")).thenReturn(cursoEntity);
		Mockito.when(this.cursoRepository.save(cursoEntity)).thenThrow(IllegalStateException.class);
		
		CursoException cursoException;
		
		cursoException = assertThrows(CursoException.class, ()->{
			this.cursoService.atualizar(cursoModel);
		});
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, cursoException.getHttpStatus());
		assertEquals(Constantes.ERRO_INTERNO, cursoException.getMessage());
		
		Mockito.verify(this.cursoRepository, times(1)).findCursoByCodigo("ENGECI");
		Mockito.verify(this.cursoRepository, times(0)).save(cursoEntity);
		
	}
	
	@Test
	void testListarThrowException() {
		Mockito.when(this.cursoRepository.findAll()).thenThrow(IllegalStateException.class);
		
		CursoException cursoException;
		
		cursoException = assertThrows(CursoException.class, ()->{
			this.cursoService.listar();
		});
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, cursoException.getHttpStatus());
		assertEquals(Constantes.ERRO_INTERNO, cursoException.getMessage());
		
		Mockito.verify(this.cursoRepository, times(1)).findAll();
	}

	@Test
	void testCadastrarThrowException() {
		
		MateriaModel materiaModel = new MateriaModel();
		materiaModel.setCodigo("ILP");
		materiaModel.setFrequencia(1);
		materiaModel.setHoras(64);
		materiaModel.setNome("ENGENHARIA DE SOFTWARE");
		
		List<Long> listaMateria = new ArrayList<>();
		
		listaMateria.add(materiaModel.getId());
		
		CursoModel cursoModel = new CursoModel();
		cursoModel.setCodigo("ENGECI");
		cursoModel.setNome("ENGENHARIA CIVIL");
		cursoModel.setMaterias(listaMateria);
		cursoEntity.setId(null);
		
		Mockito.when(this.cursoRepository.findCursoByCodigo("ENGECI")).thenReturn(null);
		Mockito.when(this.cursoRepository.save(cursoEntity)).thenThrow(IllegalStateException.class);

		CursoException cursoException;
		
		cursoException = assertThrows(CursoException.class, ()->{
			this.cursoService.cadastrar(cursoModel);
		});
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, cursoException.getHttpStatus());
		assertEquals(Constantes.ERRO_INTERNO, cursoException.getMessage());
		
		Mockito.verify(this.cursoRepository, times(1)).findCursoByCodigo("ENGECI");
		Mockito.verify(this.cursoRepository, times(0)).save(cursoEntity);
		
		cursoEntity.setId(1L);
		
	}
	
	@Test
	void testConsultarCursoThrowException() {
		Mockito.when(this.cursoRepository.findCursoByCodigo("ENGECI")).thenThrow(IllegalStateException.class);
		
		CursoException cursoException;
		
		cursoException = assertThrows(CursoException.class, ()->{
			this.cursoService.consultarPorCodigo("ENGECI");
		});
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, cursoException.getHttpStatus());
		assertEquals(Constantes.ERRO_INTERNO, cursoException.getMessage());
		
		Mockito.verify(this.cursoRepository, times(1)).findCursoByCodigo("ENGECI");
				
	}
	
	@Test
	void testExcluirThrowException() {
		Mockito.when(this.cursoRepository.findById(1L)).thenReturn(Optional.of(cursoEntity));
		Mockito.doThrow(IllegalStateException.class).when(this.cursoRepository).deleteById(1L);
		
		CursoException cursoException;
		
		cursoException = assertThrows(CursoException.class, ()->{
			this.cursoService.excluir(1L);
		});
		
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, cursoException.getHttpStatus());
		assertEquals(Constantes.ERRO_INTERNO, cursoException.getMessage());
		
		Mockito.verify(this.cursoRepository, times(1)).findById(1L);
		Mockito.verify(this.cursoRepository, times(1)).deleteById(1L);
				
	}

}
