package com.rrm14.cliente.escola.matrizcurricular.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rrm14.cliente.escola.matrizcurricular.constantes.Constantes;
import com.rrm14.cliente.escola.matrizcurricular.model.MateriaModel;
import com.rrm14.cliente.escola.matrizcurricular.model.Response;
import com.rrm14.cliente.escola.matrizcurricular.service.IMateriaService;

@RestController
@RequestMapping("/materia")
public class MateriaController {
	
	@Autowired
	private IMateriaService materiaService;

	@GetMapping("/{id}")
	public ResponseEntity<Response<MateriaModel>> consultarMateria(@PathVariable Long id){
		Response<MateriaModel> response = new Response<>();
		MateriaModel materia = this.materiaService.consultar(id);
		
		response.setData(this.materiaService.consultar(id));
		response.setStatusCode(HttpStatus.OK.value());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
				.consultarMateria(id)).withSelfRel());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
				.excluirMateria(id)).withRel(Constantes.DELETE));
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
				.atualizaMateria(materia)).withRel(Constantes.UPDATE));
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping
	public ResponseEntity<Response<List<MateriaModel>>> listarMaterias(){
		Response<List<MateriaModel>> response = new Response<>();
		response.setData(this.materiaService.listar());
		response.setStatusCode(HttpStatus.OK.value());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
				.listarMaterias()).withSelfRel());
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping
	public ResponseEntity<Response<Boolean>> cadastrarMateria(@Valid @RequestBody MateriaModel materia){	
		Response<Boolean> response = new Response<>();
		response.setData(this.materiaService.cadastrar(materia));
		response.setStatusCode(HttpStatus.CREATED.value());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);	
	}
	
	@PutMapping
	public ResponseEntity<Response<Boolean>> atualizaMateria(@Valid @RequestBody MateriaModel materia) {
		Response<Boolean> response = new Response<>();
		response.setData(materiaService.atualizar(materia));
		response.setStatusCode(HttpStatus.OK.value());
		
		return ResponseEntity.status(HttpStatus.OK).body(response);	
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Response<Boolean>> excluirMateria(@PathVariable Long id){
		Response<Boolean> response = new Response<>();
		response.setData(materiaService.excluir(id));
		response.setStatusCode(HttpStatus.OK.value());
		
		return ResponseEntity.status(HttpStatus.OK).body(response);		
	}
	
	@GetMapping("/horario-minimo/{horaMinima}")
	public ResponseEntity<Response<List<MateriaModel>>> listarMateriasPorHorarioMinimo(@PathVariable int horaMinima){
		Response<List<MateriaModel>> response = new Response<>();
		response.setData(this.materiaService.listarPorHorarioMinimo(horaMinima));
		response.setStatusCode(HttpStatus.OK.value());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
				.listarMateriasPorHorarioMinimo(horaMinima)).withSelfRel());
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/frequencia/{frequencia}")
	public ResponseEntity<Response<List<MateriaModel>>> listarMateriasPorFrequencia(@PathVariable int frequencia){
		Response<List<MateriaModel>> response = new Response<>();
		response.setData(this.materiaService.listarPorFrequencia(frequencia));
		response.setStatusCode(HttpStatus.OK.value());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
				.listarMateriasPorFrequencia(frequencia)).withSelfRel());
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
