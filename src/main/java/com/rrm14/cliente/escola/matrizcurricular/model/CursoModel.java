package com.rrm14.cliente.escola.matrizcurricular.model;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.rrm14.cliente.escola.matrizcurricular.constantes.Constantes;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CursoModel {
	
	private Long id;	
	
	@NotBlank(message = Constantes.CURSO_NOME_VAZIO)
	@Size(min=5, max=30)
	private String nome;
	
	@NotBlank(message = Constantes.CURSO_CODIGO_VAZIO)
	@Size(min=5, max=30)
	private String codigo;
	
	private List<Long> materias;

}
