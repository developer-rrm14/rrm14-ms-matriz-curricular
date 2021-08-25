package com.rrm14.cliente.escola.matrizcurricular.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.rrm14.cliente.escola.matrizcurricular.constants.Constantes;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MateriaModel {
	
	private Long id;	
	
	@NotBlank(message = Constantes.MATERIA_NOME_VAZIO)
	private String nome;
	
	@Min(value=34, message = Constantes.MATERIA_MINIMO_HORAS)
	@Max(value=120, message = Constantes.MATERIA_MAXIMO_HORAS)
	private int horas;	
	
	@NotBlank(message = Constantes.MATERIA_CODIGO_VAZIO)
	private String codigo;
	
	@Min(value=1, message = Constantes.MATERIA_MINIMO_FREQUENCIA)
	@Max(value=2, message = Constantes.MATERIA_MAXIMO_FREQUENCIA)
	private int frequencia;

}
