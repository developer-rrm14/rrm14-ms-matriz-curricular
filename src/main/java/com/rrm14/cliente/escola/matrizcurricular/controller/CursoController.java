package com.rrm14.cliente.escola.matrizcurricular.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.rrm14.cliente.escola.matrizcurricular.entity.CursoEntity;
import com.rrm14.cliente.escola.matrizcurricular.model.CursoModel;
import com.rrm14.cliente.escola.matrizcurricular.model.Response;
import com.rrm14.cliente.escola.matrizcurricular.service.ICursoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@Api(tags = SwaggerConfig.CURSO)
@RestController
@RequestMapping("/curso")
public class CursoController {
	
	@Autowired
	private ICursoService cursoService;

	@ApiOperation(value = Constantes.SWAGGER_CONSULTAR_CODIGO_CURSO)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = Constantes.SWAGGER_MENSAGEM_200),
			@ApiResponse(code = 404, message = Constantes.SWAGGER_MENSAGEM_404),
			@ApiResponse(code = 500, message = Constantes.SWAGGER_MENSAGEM_500),
	})
	@GetMapping("/{codigo}")
	public ResponseEntity<Response<CursoEntity>> consultarCursoPorCodigo(@PathVariable String codigo) {
		Response<CursoEntity> response = new Response<>();
		response.setData(this.cursoService.consultarPorCodigo(codigo));
		response.setStatusCode(HttpStatus.OK.value());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@ApiOperation(value = Constantes.SWAGGER_LISTAR_CURSO)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = Constantes.SWAGGER_MENSAGEM_200),
			@ApiResponse(code = 500, message = Constantes.SWAGGER_MENSAGEM_500),
	})
	@GetMapping
	public ResponseEntity<Response<List<CursoEntity>>> listarCursos(){
		Response<List<CursoEntity>> response = new Response<>();
		response.setData(this.cursoService.listar());
		response.setStatusCode(HttpStatus.OK.value());
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@ApiOperation(value = Constantes.SWAGGER_CADASTRAR_CURSO)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = Constantes.SWAGGER_MENSAGEM_201),
			@ApiResponse(code = 400, message = Constantes.SWAGGER_MENSAGEM_400),
			@ApiResponse(code = 500, message = Constantes.SWAGGER_MENSAGEM_500),
	})
	@PostMapping
	public ResponseEntity<Response<Boolean>> cadastrarCurso(@Valid @RequestBody CursoModel curso){	
		Response<Boolean> response = new Response<>();
		response.setData(this.cursoService.cadastrar(curso));
		response.setStatusCode(HttpStatus.CREATED.value());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);	
	}
	
	@ApiOperation(value = Constantes.SWAGGER_ATUALIZAR_CURSO)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = Constantes.SWAGGER_MENSAGEM_200),
			@ApiResponse(code = 400, message = Constantes.SWAGGER_MENSAGEM_400),
			@ApiResponse(code = 404, message = Constantes.SWAGGER_MENSAGEM_404),
			@ApiResponse(code = 500, message = Constantes.SWAGGER_MENSAGEM_500),
	})
	@PutMapping
	public ResponseEntity<Response<Boolean>> atualizaCurso(@Valid @RequestBody CursoModel curso) {
		Response<Boolean> response = new Response<>();
		response.setData(cursoService.atualizar(curso));
		response.setStatusCode(HttpStatus.OK.value());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@ApiOperation(value = Constantes.SWAGGER_EXCLUIR_CURSO)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = Constantes.SWAGGER_MENSAGEM_200),
			@ApiResponse(code = 404, message = Constantes.SWAGGER_MENSAGEM_404),
			@ApiResponse(code = 500, message = Constantes.SWAGGER_MENSAGEM_500),
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Response<Boolean>> excluirCurso(@PathVariable Long id) {
		Response<Boolean> response = new Response<>();
		response.setData(cursoService.excluir(id));
		response.setStatusCode(HttpStatus.OK.value());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	 
	
	
}
