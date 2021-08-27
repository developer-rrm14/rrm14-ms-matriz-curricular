package com.rrm14.cliente.escola.matrizcurricular.constants;

public final class Constantes {
	
	private Constantes(){}

	
	public static final String USUARIO_AUTENTICACAO         = "moduretick";
	public static final String SENHA_AUTENTICACAO           = "moduretick";
	public static final String PAPEL_USUARIO                = "ADMIN";
	public static final String MATERIA_NAO_ENCONTRADA       = "Matéria Não Encontrada.";
	public static final String CURSO_NAO_ENCONTRADO         = "Curso Não Encontrado.";
	public static final String MATERIA_NOME_VAZIO           = "Informe o Nome da Matéria.";
	public static final String MATERIA_CODIGO_VAZIO         = "Informe o Código da Matéria.";
	public static final String MATERIA_VIOLACAO_REMOCAO     = "Matéria Não Pode ser Removida";
	public static final String MATERIA_JA_CADASTRADA        = "Matéria Já Cadastrado Anteriormente.";
	public static final String CURSO_NOME_VAZIO             = "Informe o Nome do Curso";
	public static final String CURSO_CODIGO_VAZIO           = "Informe o Código do Curso";
	public static final String MATERIA_MINIMO_HORAS         = "Permitido o Mínimo de 34 Horas por Matéria";
	public static final String MATERIA_MAXIMO_HORAS         = "Permitido o Máximo de 120 Horas por Matéria";
	public static final String MATERIA_MINIMO_FREQUENCIA    = "Permitido o Mínimo de 1 Vez ao Ano";
	public static final String MATERIA_MAXIMO_FREQUENCIA    = "Permitido o Mínimo de 2 Vezes ao Ano";
	public static final String ERRO_INTERNO                 = "Erro Interno Identificado. Contate o Suporte";
	public static final String ERRO_ID_INFORMADO            = "ID Não Pode Ser Informado.";
	public static final String CURSO_JA_CADASTRADO          = "Curso Já Cadastrado Anteriormente.";
	public static final String DELETE                       = "DELETE";
	public static final String UPDATE                       = "UPDATE";
	public static final String SWAGGER_CADASTRAR_CURSO            = "Cadastrar um Novo Curso"; 
	public static final String SWAGGER_LISTAR_CURSO               = "Listar Todos os Cursos Cadastrados";
	public static final String SWAGGER_CONSULTAR_CODIGO_CURSO     = "Consultar Curso por Código";
	public static final String SWAGGER_ATUALIZAR_CURSO            = "Atualizar o Curso Informado";
	public static final String SWAGGER_EXCLUIR_CURSO              = "Excluir o Curso Informado";
	public static final String SWAGGER_CADASTRAR_MATERIA          = "Cadastrar uma Nova Matéria"; 
	public static final String SWAGGER_LISTAR_MATERIA             = "Listar Todas as Matérias Cadastradas";
	public static final String SWAGGER_LISTAR_MATERIA_HORA        = "Listar Todas as Matérias por Hora Minima";
	public static final String SWAGGER_LISTAR_MATERIA_FREQUENCIA  = "Listar Todas as Matérias por Hora Minima";
	public static final String SWAGGER_CONSULTAR_ID_MATERIA       = "Consultar Matéria por ID";
	public static final String SWAGGER_ATUALIZAR_MATERIA          = "Atualizar a Matéria Informada";
	public static final String SWAGGER_EXCLUIR_MATERIA            = "Excluir a Matéria Informada";
	public static final String SWAGGER_MENSAGEM_200               = "Requisição Executada com Sucesso";
	public static final String SWAGGER_MENSAGEM_201               = "Entidade Criada com Sucesso";
	public static final String SWAGGER_MENSAGEM_400               = "Erro na Requisição Enviada Pelo Cliente";
	public static final String SWAGGER_MENSAGEM_404               = "Resultado Não Encontrado";
	public static final String SWAGGER_MENSAGEM_500               = "Erro Interno no Serviço";
	
}
