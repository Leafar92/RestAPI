package com.challenge.food.api.exception;

public enum ProblemType {

	ENTIDADE_NAO_ENCONTRADA("Entidade nao encontrada", "/entidade-nao-econtrada"),
	CORPO_MENSAGEM_INVALIDO("Corpo mensagem incorreta ", "/corpomensagem-incorreta"),
	ERRO_DE_SISTEMA("Erro de sistema", "/erro-de-sistema"),
	PARAMETRO_INVALIDO("Parametro invalido ", "/parametro-invalido"),
	DADOS_INVALIDOS("Dados invalidos", "/dados-invalidos"),
	RECURSO_JA_EXISTE("Recurso ja existe", "/recurso-ja-cadastrado"),
	RECURSO_EM_USO("Rescurso em uso", "/recurso-em-uso"),
	VIOLACAO_DE_NEGOCIO("Violacao de negocio", "/violacao-de-negocio");
	
	private String title;
	private String uri;
	
	private ProblemType(String title, String path) {
		this.title = title;
		this.uri = "http://algafood.com.br" + path;
	}

	public String getTitle() {
		return title;
	}

	public String getUri() {
		return uri;
	}
	
	
}
