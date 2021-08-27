package com.rrm14.cliente.escola.matrizcurricular.controller.test;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
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

import com.rrm14.cliente.escola.matrizcurricular.constants.Constantes;
import com.rrm14.cliente.escola.matrizcurricular.entity.CursoEntity;
import com.rrm14.cliente.escola.matrizcurricular.entity.MateriaEntity;
import com.rrm14.cliente.escola.matrizcurricular.model.CursoModel;
import com.rrm14.cliente.escola.matrizcurricular.model.Response;
import com.rrm14.cliente.escola.matrizcurricular.repository.ICursoRepository;
import com.rrm14.cliente.escola.matrizcurricular.repository.IMateriaRepository;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(JUnitPlatform.class)
public class CursoControllerIntegratedTest {
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private ICursoRepository cursoRepository;
	
	@Autowired
	private IMateriaRepository materiaRepository;
	
	@BeforeEach
	public void init() {
		this.montaMateriaBaseDeDados();
		this.montaCursoBaseDeDados();
	}
	
	@AfterEach
	public void finish() {
		this.cursoRepository.deleteAll();
		this.materiaRepository.deleteAll();
	}
	
	private void montaMateriaBaseDeDados() {
		MateriaEntity m1 = new MateriaEntity();
		m1.setCodigo("ILP");
		m1.setFrequencia(2);
		m1.setHoras(64);
		m1.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");

		MateriaEntity m2 = new MateriaEntity();
		m2.setCodigo("POO");
		m2.setFrequencia(2);
		m2.setHoras(84);
		m2.setNome("PROGRAMACAO ORIENTADA A OBJETOS");

		MateriaEntity m3 = new MateriaEntity();
		m3.setCodigo("APA");
		m3.setFrequencia(1);
		m3.setHoras(102);
		m3.setNome("ANALISE E PROJETOS DE ALGORITMOS");

		this.materiaRepository.saveAll(Arrays.asList(m1, m2, m3));
	}
	
	private void montaCursoBaseDeDados() {

		List<MateriaEntity> listMaterias = this.materiaRepository.findAll();

		CursoEntity c1 = new CursoEntity();
		c1.setCodigo("ENGCI");
		c1.setNome("ENGENHARIA DA COMPUTACAO");
		c1.setMaterias(listMaterias);

		this.cursoRepository.save(c1);
	}
	
	private String montaUri(String urn) {
		return "http://localhost:" + this.port + "/curso/" + urn;
	}
	
	@Test
	void testListarCursos() {
		
		ResponseEntity<Response<List<CursoEntity>>> cursos = restTemplate
				.withBasicAuth(Constantes.USUARIO_AUTENTICACAO, Constantes.SENHA_AUTENTICACAO)
				.exchange(this.montaUri(""), HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<List<CursoEntity>>>() {
				});
		
		assertNotNull(cursos.getBody().getData());
		assertEquals(1, cursos.getBody().getData().size());
		assertEquals(3, cursos.getBody().getData().get(0).getMaterias().size());
		assertEquals(200, cursos.getBody().getStatusCode());
				
	}
	
	
	@Test
	void testConsultarCurso() {
		
		List<CursoEntity> cursoList = this.cursoRepository.findAll();
		String codigo = cursoList.get(0).getCodigo();
		
		ResponseEntity<Response<CursoEntity>> cursos = restTemplate
				.withBasicAuth(Constantes.USUARIO_AUTENTICACAO, Constantes.SENHA_AUTENTICACAO)
				.exchange(this.montaUri(codigo), HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<CursoEntity>>() {
				});
		
		assertNotNull(cursos.getBody().getData());
		assertEquals(codigo, cursos.getBody().getData().getCodigo());
		assertEquals("ENGENHARIA DA COMPUTACAO", cursos.getBody().getData().getNome());
		assertEquals(200, cursos.getBody().getStatusCode());
				
	}
	
	@Test
	void testCadastrarCurso() {
		
		List<MateriaEntity> listaMateria = this.materiaRepository.findAll();
		List<Long> idList = new ArrayList<>();
		
		idList.add(listaMateria.get(0).getId());
		idList.add(listaMateria.get(1).getId());
		
		CursoModel c4 = new CursoModel();
		c4.setCodigo("MATEM");
		c4.setNome("MATEMATICA FINANCEIRA");
		c4.setMaterias(idList);
		
		HttpEntity<CursoModel> request = new HttpEntity<>(c4);
		
		ResponseEntity<Response<Boolean>> cursos = restTemplate
				.withBasicAuth(Constantes.USUARIO_AUTENTICACAO, Constantes.SENHA_AUTENTICACAO)
				.exchange(this.montaUri(""), HttpMethod.POST, request,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});
		
		List<CursoEntity> listCursoCadastrado = this.cursoRepository.findAll();
		
		assertTrue(cursos.getBody().getData());
		assertEquals(2, listCursoCadastrado.size());
		assertEquals(201, cursos.getBody().getStatusCode());
				
	}
	
	
	
	@Test
	void testAtualizarCurso() {
		
		List<CursoEntity> cursosList = this.cursoRepository.findAll();
		CursoEntity cursoEntity = cursosList.get(0);

		CursoModel cursoModel = new CursoModel();

		cursoModel.setId(cursoEntity.getId());
		cursoModel.setCodigo(cursoEntity.getCodigo());
		cursoModel.setNome("Teste Atualiza curso");

		HttpEntity<CursoModel> request = new HttpEntity<>(cursoModel);
	
		ResponseEntity<Response<Boolean>> cursos = restTemplate
				.withBasicAuth(Constantes.USUARIO_AUTENTICACAO, Constantes.SENHA_AUTENTICACAO)
				.exchange(this.montaUri(""), HttpMethod.PUT, request,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});
		CursoEntity cursoAtualizado = this.cursoRepository.findCursoByCodigo(cursoEntity.getCodigo());

		assertTrue(cursos.getBody().getData());
		assertEquals("Teste Atualiza curso", cursoAtualizado.getNome());
		assertEquals(200, cursos.getBody().getStatusCode());
				
	}
	
	
	@Test
	void testExcluirCurso() {
		
		List<CursoEntity> cursoList = this.cursoRepository.findAll();
		Long id = cursoList.get(0).getId();
		
		ResponseEntity<Response<Boolean>> cursos = restTemplate
				.withBasicAuth(Constantes.USUARIO_AUTENTICACAO, Constantes.SENHA_AUTENTICACAO)
				.exchange(this.montaUri(id.toString()), HttpMethod.DELETE, null,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});
		
		List<CursoEntity> listCursoExcluido = this.cursoRepository.findAll();
	
		assertTrue(cursos.getBody().getData());
		assertEquals(0, listCursoExcluido.size());
		assertEquals(200, cursos.getBody().getStatusCode());
				
	}
	
	

}
