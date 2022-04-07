package br.com.alura.forum.controller.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.alura.forum.modelo.Resposta;
import br.com.alura.forum.modelo.StatusTopico;
import br.com.alura.forum.modelo.Topico;

public class DetalhesTopicoDTO {private Long id;
private String titulo;
private String mensagem;
private LocalDateTime dataCriacao;
private String nomeAutor;
private StatusTopico status;
private List<RespostaDTO> resposta;

public DetalhesTopicoDTO(Topico topico) {
	this.id = topico.getId();
	this.titulo = topico.getTitulo();
	this.mensagem = topico.getMensagem();
	this.dataCriacao = topico.getDataCriacao();
	this.status = topico.getStatus();
	this.nomeAutor = topico.getAutor().getNome();
	this.resposta = new ArrayList<RespostaDTO>();
	this.resposta.addAll(topico.getRespostas().stream().map(RespostaDTO::new).collect(Collectors.toList()));
}
public Long getId() {
	return id;
}
public String getTitulo() {
	return titulo;
}
public String getMensagem() {
	return mensagem;
}
public LocalDateTime getDataCriacao() {
	return dataCriacao;
}
public static List<TopicoDTO> conver(List<Topico> topicos) {
	return topicos.stream().map(TopicoDTO::new).collect(Collectors.toList());
}
public String getNomeAutor() {
	return nomeAutor;
}
public StatusTopico getStatus() {
	return status;
}
public List<RespostaDTO> getResposta() {
	return resposta;
}
}
