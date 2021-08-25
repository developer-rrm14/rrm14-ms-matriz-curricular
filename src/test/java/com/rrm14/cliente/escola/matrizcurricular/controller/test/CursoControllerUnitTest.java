package com.rrm14.cliente.escola.matrizcurricular.controller.test;


import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.rrm14.cliente.escola.matrizcurricular.entity.CursoEntity;
import com.rrm14.cliente.escola.matrizcurricular.entity.MateriaEntity;
import com.rrm14.cliente.escola.matrizcurricular.model.CursoModel;
import com.rrm14.cliente.escola.matrizcurricular.model.MateriaModel;
import com.rrm14.cliente.escola.matrizcurricular.model.Response;
import com.rrm14.cliente.escola.matrizcurricular.service.ICursoService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(JUnitPlatform.class)
public class CursoControllerUnitTest {
	
	@LocalServerPort
	private int port;
	
	@MockBean
	private ICursoService cursoService;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	private static CursoModel cursoModel;
	
	private static MateriaModel materiaModel;
	
	private static CursoEntity cursoEntity;
	
	private static MateriaEntity materiaEntity;
	
	@BeforeAll
	public static void init() {
		
		materiaModel = new MateriaModel();
		materiaModel.setId(1L);
		materiaModel.setCodigo("ILP");
		materiaModel.setFrequencia(1);
		materiaModel.setHoras(64);
		materiaModel.setNome("Engenharia de Software");
		
		List<Long> listaMateria = new ArrayList<>();
		
		listaMateria.add(materiaModel.getId());
		
		cursoModel = new CursoModel();
		cursoModel.setId(1L);
		cursoModel.setCodigo("ENGECI");
		cursoModel.setNome("ENGENHARIA CIVIL");
		cursoModel.setMaterias(listaMateria);
		
		materiaEntity = new MateriaEntity();
		materiaEntity.setId(1L);
		materiaEntity.setCodigo("ILP");
		materiaEntity.setFrequencia(1);
		materiaEntity.setHoras(64);
		materiaEntity.setNome("Engenharia de Software");
		
		List<MateriaEntity> listaMateriaEntity = new ArrayList<>();
		
		listaMateriaEntity.add(materiaEntity);
		
		cursoEntity = new CursoEntity();
		cursoEntity.setId(1L);
		cursoEntity.setCodigo("ENGECI");
		cursoEntity.setNome("ENGENHARIA CIVIL");
		cursoEntity.setMaterias(listaMateriaEntity);
		
	}
	
	@Test
	void testListarCursos() {
		Mockito.when(this.cursoService.listar()).thenReturn(new ArrayList<CursoEntity>());
		
		ResponseEntity<Response<List<CursoEntity>>> cursos = restTemplate
				.exchange("http://localhost:" + this.port + "/curso/", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<List<CursoEntity>>>() {
				});
		
		assertNotNull(cursos.getBody().getData());
		assertEquals(200, cursos.getBody().getStatusCode());
				
	}
	
	@Test
	void testConsultarCurso() {
		Mockito.when(this.cursoService.consultarPorCodigo("ENGECI")).thenReturn(cursoEntity);
		
		ResponseEntity<Response<CursoEntity>> cursos = restTemplate
				.exchange("http://localhost:" + this.port + "/curso/ENGECI", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<CursoEntity>>() {
				});
		
		assertNotNull(cursos.getBody().getData());
		assertEquals(200, cursos.getBody().getStatusCode());
				
	}
	
	@Test
	void testCadastrarCursos() {
		Mockito.when(this.cursoService.cadastrar(cursoModel)).thenReturn(Boolean.TRUE);
		
		HttpEntity<CursoModel> request = new HttpEntity<>(cursoModel);
		
		ResponseEntity<Response<Boolean>> cursos = restTemplate
				.exchange("http://localhost:" + this.port + "/curso/", HttpMethod.POST, request,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});
		
		assertNotNull(cursos.getBody().getData());
		assertEquals(201, cursos.getBody().getStatusCode());
				
	}
	
	@Test
	void testAtualizarCursos() {
		Mockito.when(this.cursoService.atualizar(cursoModel)).thenReturn(Boolean.TRUE);
		
		HttpEntity<CursoModel> request = new HttpEntity<>(cursoModel);
		
		ResponseEntity<Response<Boolean>> cursos = restTemplate
				.exchange("http://localhost:" + this.port + "/curso/", HttpMethod.PUT, request,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});
		
		assertNotNull(cursos.getBody().getData());
		assertEquals(200, cursos.getBody().getStatusCode());
				
	}
	
	@Test
	void testExcluirCursos() {
		Mockito.when(this.cursoService.excluir(1L)).thenReturn(Boolean.TRUE);
		
		ResponseEntity<Response<Boolean>> cursos = restTemplate
				.exchange("http://localhost:" + this.port + "/curso/1", HttpMethod.DELETE, null,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});
		
		assertNotNull(cursos.getBody().getData());
		assertEquals(200, cursos.getBody().getStatusCode());
				
	}
	
	
	
	

}
