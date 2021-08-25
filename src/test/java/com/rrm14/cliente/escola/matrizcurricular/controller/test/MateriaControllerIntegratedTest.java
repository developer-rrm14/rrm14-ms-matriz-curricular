package com.rrm14.cliente.escola.matrizcurricular.controller.test;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.rrm14.cliente.escola.matrizcurricular.entity.MateriaEntity;
import com.rrm14.cliente.escola.matrizcurricular.model.MateriaModel;
import com.rrm14.cliente.escola.matrizcurricular.model.Response;
import com.rrm14.cliente.escola.matrizcurricular.repository.IMateriaRepository;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(JUnitPlatform.class)
public class MateriaControllerIntegratedTest {
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private IMateriaRepository materiaRepository;
	
	@BeforeEach
	public void init() {
		this.montaBaseDeDados();
	}
	
	@AfterEach
	public void finish() {
		this.materiaRepository.deleteAll();
	}
	
	private void montaBaseDeDados(){
		
		MateriaEntity m1 = new MateriaEntity();
		m1.setCodigo("ILP");
		m1.setFrequencia(2);
		m1.setHoras(64);
		m1.setNome("INTRODUÇÃO A LINGUAGEM DE PROGRAMAÇÃO");
		
		MateriaEntity m2 = new MateriaEntity();
		m2.setCodigo("POO");
		m2.setFrequencia(1);
		m2.setHoras(85);
		m2.setNome("PROGRAMAÇÃO ORIENTADA A OBJETOS");
		
		MateriaEntity m3 = new MateriaEntity();
		m3.setCodigo("APA");
		m3.setFrequencia(2);
		m3.setHoras(102);
		m3.setNome("ANALISE E PROJETOS DE ALGORITMOS");
		
		this.materiaRepository.saveAll(Arrays.asList(m1,m2,m3));
	}
	
	@Test
	void testListarMaterias() {
		
		ResponseEntity<Response<List<MateriaModel>>> materias = restTemplate
				.exchange("http://localhost:" + this.port + "/materia/", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<List<MateriaModel>>>() {
				});
		
		assertNotNull(materias.getBody().getData());
		assertEquals(3, materias.getBody().getData().size());
		assertEquals(200, materias.getBody().getStatusCode());
				
	}
	
	@Test
	void testConsultarMateriasPorHoraMinima() {
		
		ResponseEntity<Response<List<MateriaModel>>> materias = restTemplate
				.exchange("http://localhost:" + this.port + "/materia/horario-minimo/80", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<List<MateriaModel>>>() {
				});
		
		assertNotNull(materias.getBody().getData());
		assertEquals(2, materias.getBody().getData().size());
		assertEquals(200, materias.getBody().getStatusCode());
				
	}
	
	@Test
	void testConsultarMateriasPorFrequencia() {
		
		ResponseEntity<Response<List<MateriaModel>>> materias = restTemplate
				.exchange("http://localhost:" + this.port + "/materia/frequencia/1", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<List<MateriaModel>>>() {
				});
		
		assertNotNull(materias.getBody().getData());
		assertEquals(1, materias.getBody().getData().size());
		assertEquals(200, materias.getBody().getStatusCode());
				
	}
	
	@Test
	void testConsultarMateria() {
		
		List<MateriaEntity> materiaList = this.materiaRepository.findAll();
		Long id = materiaList.get(0).getId();
		
		ResponseEntity<Response<MateriaModel>> materias = restTemplate
				.exchange("http://localhost:" + this.port + "/materia/"+id, HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<MateriaModel>>() {
				});
		
		assertNotNull(materias.getBody().getData());
		assertEquals(id, materias.getBody().getData().getId());
		assertEquals("ILP", materias.getBody().getData().getCodigo());
		assertEquals(2, materias.getBody().getData().getFrequencia());
		assertEquals(64, materias.getBody().getData().getHoras());
		assertEquals("INTRODUÇÃO A LINGUAGEM DE PROGRAMAÇÃO", materias.getBody().getData().getNome());
		assertEquals(200, materias.getBody().getStatusCode());
				
	}
	
	@Test
	void testCadastrarMateria() {
		
		MateriaEntity m4 = new MateriaEntity();
		m4.setCodigo("CALC1");
		m4.setFrequencia(1);
		m4.setHoras(95);
		m4.setNome("CALCULO 01");
		
		HttpEntity<MateriaEntity> request = new HttpEntity<>(m4);
		
		ResponseEntity<Response<Boolean>> materias = restTemplate
				.exchange("http://localhost:" + this.port + "/materia/", HttpMethod.POST, request,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});
		
		List<MateriaEntity> listMateriaCadastrada = this.materiaRepository.findAll();
		
		assertTrue(materias.getBody().getData());
		assertEquals(4, listMateriaCadastrada.size());
		assertEquals(201, materias.getBody().getStatusCode());
				
	}
	
	@Test
	void testAtualizarMateria() {
		
		List<MateriaEntity> materiaList = this.materiaRepository.findAll();
		MateriaEntity materia = materiaList.get(0);
		
		materia.setNome("Teste Atualiza Materia");
		
		HttpEntity<MateriaEntity> request = new HttpEntity<>(materia);
		
		ResponseEntity<Response<Boolean>> materias = restTemplate
				.exchange("http://localhost:" + this.port + "/materia/", HttpMethod.PUT, request,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});
		
		MateriaEntity materiaAtualizada = this.materiaRepository.findById(materia.getId()).get();
		
		assertTrue(materias.getBody().getData());
		assertEquals("Teste Atualiza Materia", materiaAtualizada.getNome());
		assertEquals(200, materias.getBody().getStatusCode());
				
	}
	
	
	@Test
	void testExcluirMateria() {
		
		List<MateriaEntity> materiaList = this.materiaRepository.findAll();
		Long id = materiaList.get(0).getId();
		
		ResponseEntity<Response<Boolean>> materias = restTemplate
				.exchange("http://localhost:" + this.port + "/materia/"+id, HttpMethod.DELETE, null,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});
		
		List<MateriaEntity> listMateriaExcluida = this.materiaRepository.findAll();
	
		assertTrue(materias.getBody().getData());
		assertEquals(2, listMateriaExcluida.size());
		assertEquals(200, materias.getBody().getStatusCode());
				
	}
	
	

}
