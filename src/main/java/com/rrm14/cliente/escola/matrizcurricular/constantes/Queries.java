package com.rrm14.cliente.escola.matrizcurricular.constantes;

public class Queries {
	
	private Queries(){}
	
	public static final String QUERY_MATERIA_HORA_MINIMA    = "select m from MateriaEntity m where m.horas >= :horaMinima";
	public static final String QUERY_MATERIA_FREQUENCIA     = "select m from MateriaEntity m where m.frequencia = :frequencia";
	public static final String QUERY_CURSO_POR_CODIGO       = "select c from CursoEntity c where c.codigo = :codigo";

}
