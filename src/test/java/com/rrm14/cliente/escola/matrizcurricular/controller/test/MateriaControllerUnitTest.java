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

import com.rrm14.cliente.escola.matrizcurricular.constants.Constantes;
import com.rrm14.cliente.escola.matrizcurricular.model.MateriaModel;
import com.rrm14.cliente.escola.matrizcurricular.model.Response;
import com.rrm14.cliente.escola.matrizcurricular.service.IMateriaService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(JUnitPlatform.class)
public class MateriaControllerUnitTest {
	
	@LocalServerPort
	private int port;
	
	@MockBean
	private IMateriaService materiaService;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	private static MateriaModel materiaModel;
	
	@BeforeAll
	public static void init() {
		
		materiaModel = new MateriaModel();
		materiaModel.setId(1L);
		materiaModel.setCodigo("ILP");
		materiaModel.setFrequencia(1);
		materiaModel.setHoras(64);
		materiaModel.setNome("Engenharia de Software");
		
	}
	
	@Test
	void testListarMaterias() {
		Mockito.when(this.materiaService.listar()).thenReturn(new ArrayList<MateriaModel>());
		
		ResponseEntity<Response<List<MateriaModel>>> materias = restTemplate
				.withBasicAuth(Constantes.USUARIO_AUTENTICACAO, Constantes.SENHA_AUTENTICACAO)
				.exchange("http://localhost:" + this.port + "/materia/", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<List<MateriaModel>>>() {
				});
		
		assertNotNull(materias.getBody().getData());
		assertEquals(200, materias.getBody().getStatusCode());
				
	}
	
	@Test
	void testConsultarMateria() {
		Mockito.when(this.materiaService.consultar(1L)).thenReturn(materiaModel);
		
		ResponseEntity<Response<MateriaModel>> materias = restTemplate
				.withBasicAuth(Constantes.USUARIO_AUTENTICACAO, Constantes.SENHA_AUTENTICACAO)
				.exchange("http://localhost:" + this.port + "/materia/1", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<MateriaModel>>() {
				});
		
		assertNotNull(materias.getBody().getData());
		assertEquals(200, materias.getBody().getStatusCode());
				
	}
	
	@Test
	void testCadastrarMaterias() {
		Mockito.when(this.materiaService.cadastrar(materiaModel)).thenReturn(Boolean.TRUE);
		
		HttpEntity<MateriaModel> request = new HttpEntity<>(materiaModel);
		
		ResponseEntity<Response<Boolean>> materias = restTemplate
				.withBasicAuth(Constantes.USUARIO_AUTENTICACAO, Constantes.SENHA_AUTENTICACAO)
				.exchange("http://localhost:" + this.port + "/materia/", HttpMethod.POST, request,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});
		
		assertNotNull(materias.getBody().getData());
		assertEquals(201, materias.getBody().getStatusCode());
				
	}
	
	@Test
	void testAtualizarMaterias() {
		Mockito.when(this.materiaService.atualizar(materiaModel)).thenReturn(Boolean.TRUE);
		
		HttpEntity<MateriaModel> request = new HttpEntity<>(materiaModel);
		
		ResponseEntity<Response<Boolean>> materias = restTemplate
				.withBasicAuth(Constantes.USUARIO_AUTENTICACAO, Constantes.SENHA_AUTENTICACAO)
				.exchange("http://localhost:" + this.port + "/materia/", HttpMethod.PUT, request,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});
		
		assertNotNull(materias.getBody().getData());
		assertEquals(200, materias.getBody().getStatusCode());
				
	}
	
	@Test
	void testExcluirMaterias() {
		Mockito.when(this.materiaService.excluir(1L)).thenReturn(Boolean.TRUE);
		
		ResponseEntity<Response<Boolean>> materias = restTemplate
				.withBasicAuth(Constantes.USUARIO_AUTENTICACAO, Constantes.SENHA_AUTENTICACAO)
				.exchange("http://localhost:" + this.port + "/materia/1", HttpMethod.DELETE, null,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});
		
		assertNotNull(materias.getBody().getData());
		assertEquals(200, materias.getBody().getStatusCode());
				
	}
	
	@Test
	void testConsultarMateriasPorHoraMinima() {
		Mockito.when(this.materiaService.listarPorHorarioMinimo(64)).thenReturn(new ArrayList<MateriaModel>());
		
		ResponseEntity<Response<List<MateriaModel>>> materias = restTemplate
				.withBasicAuth(Constantes.USUARIO_AUTENTICACAO, Constantes.SENHA_AUTENTICACAO)
				.exchange("http://localhost:" + this.port + "/materia/horario-minimo/64", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<List<MateriaModel>>>() {
				});
		
		assertNotNull(materias.getBody().getData());
		assertEquals(200, materias.getBody().getStatusCode());
				
	}
	
	@Test
	void testConsultarMateriasPorFrequencia() {
		Mockito.when(this.materiaService.listarPorFrequencia(1)).thenReturn(new ArrayList<MateriaModel>());
		
		ResponseEntity<Response<List<MateriaModel>>> materias = restTemplate
				.withBasicAuth(Constantes.USUARIO_AUTENTICACAO, Constantes.SENHA_AUTENTICACAO)
				.exchange("http://localhost:" + this.port + "/materia/frequencia/1", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<List<MateriaModel>>>() {
				});
		
		assertNotNull(materias.getBody().getData());
		assertEquals(200, materias.getBody().getStatusCode());
				
	}

}
