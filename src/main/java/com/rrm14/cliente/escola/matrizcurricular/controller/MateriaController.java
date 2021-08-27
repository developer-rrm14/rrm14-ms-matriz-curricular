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

import com.rrm14.cliente.escola.matrizcurricular.config.SwaggerConfig;
import com.rrm14.cliente.escola.matrizcurricular.constants.Constantes;
import com.rrm14.cliente.escola.matrizcurricular.model.MateriaModel;
import com.rrm14.cliente.escola.matrizcurricular.model.Response;
import com.rrm14.cliente.escola.matrizcurricular.service.IMateriaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = SwaggerConfig.MATERIA)
@RestController
@RequestMapping("/materia")
public class MateriaController {
	
	@Autowired
	private IMateriaService materiaService;

	@ApiOperation(value = Constantes.SWAGGER_CONSULTAR_ID_MATERIA)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = Constantes.SWAGGER_MENSAGEM_200),
			@ApiResponse(code = 404, message = Constantes.SWAGGER_MENSAGEM_404),
			@ApiResponse(code = 500, message = Constantes.SWAGGER_MENSAGEM_500),
	})
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
				.atualizarMateria(materia)).withRel(Constantes.UPDATE));
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@ApiOperation(value = Constantes.SWAGGER_LISTAR_MATERIA)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = Constantes.SWAGGER_MENSAGEM_200),
			@ApiResponse(code = 500, message = Constantes.SWAGGER_MENSAGEM_500),
	})
	@GetMapping
	public ResponseEntity<Response<List<MateriaModel>>> listarMaterias(){
		Response<List<MateriaModel>> response = new Response<>();
		response.setData(this.materiaService.listar());
		response.setStatusCode(HttpStatus.OK.value());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
				.listarMaterias()).withSelfRel());
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@ApiOperation(value = Constantes.SWAGGER_CADASTRAR_MATERIA)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = Constantes.SWAGGER_MENSAGEM_201),
			@ApiResponse(code = 400, message = Constantes.SWAGGER_MENSAGEM_400),
			@ApiResponse(code = 500, message = Constantes.SWAGGER_MENSAGEM_500),
	})
	@PostMapping
	public ResponseEntity<Response<Boolean>> cadastrarMateria(@Valid @RequestBody MateriaModel materia){	
		Response<Boolean> response = new Response<>();
		response.setData(this.materiaService.cadastrar(materia));
		response.setStatusCode(HttpStatus.CREATED.value());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);	
	}
	
	@ApiOperation(value = Constantes.SWAGGER_ATUALIZAR_MATERIA)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = Constantes.SWAGGER_MENSAGEM_200),
			@ApiResponse(code = 400, message = Constantes.SWAGGER_MENSAGEM_400),
			@ApiResponse(code = 404, message = Constantes.SWAGGER_MENSAGEM_404),
			@ApiResponse(code = 500, message = Constantes.SWAGGER_MENSAGEM_500),
	})
	@PutMapping
	public ResponseEntity<Response<Boolean>> atualizarMateria(@Valid @RequestBody MateriaModel materia) {
		Response<Boolean> response = new Response<>();
		response.setData(materiaService.atualizar(materia));
		response.setStatusCode(HttpStatus.OK.value());
		
		return ResponseEntity.status(HttpStatus.OK).body(response);	
	}
	
	@ApiOperation(value = Constantes.SWAGGER_EXCLUIR_MATERIA)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = Constantes.SWAGGER_MENSAGEM_200),
			@ApiResponse(code = 404, message = Constantes.SWAGGER_MENSAGEM_404),
			@ApiResponse(code = 500, message = Constantes.SWAGGER_MENSAGEM_500),
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Response<Boolean>> excluirMateria(@PathVariable Long id){
		Response<Boolean> response = new Response<>();
		response.setData(materiaService.excluir(id));
		response.setStatusCode(HttpStatus.OK.value());
		
		return ResponseEntity.status(HttpStatus.OK).body(response);		
	}
	
	@ApiOperation(value = Constantes.SWAGGER_LISTAR_MATERIA_HORA)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = Constantes.SWAGGER_MENSAGEM_200),
			@ApiResponse(code = 500, message = Constantes.SWAGGER_MENSAGEM_500),
	})
	@GetMapping("/horario-minimo/{horaMinima}")
	public ResponseEntity<Response<List<MateriaModel>>> listarMateriasPorHorarioMinimo(@PathVariable int horaMinima){
		Response<List<MateriaModel>> response = new Response<>();
		response.setData(this.materiaService.listarPorHorarioMinimo(horaMinima));
		response.setStatusCode(HttpStatus.OK.value());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
				.listarMateriasPorHorarioMinimo(horaMinima)).withSelfRel());
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@ApiOperation(value = Constantes.SWAGGER_LISTAR_MATERIA_FREQUENCIA)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = Constantes.SWAGGER_MENSAGEM_200),
			@ApiResponse(code = 500, message = Constantes.SWAGGER_MENSAGEM_500),
	})
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
