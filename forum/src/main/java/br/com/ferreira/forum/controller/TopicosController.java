package br.com.ferreira.forum.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import br.com.ferreira.forum.controller.dto.TopicoDTO;

@RestController
public class TopicosController {

	@RequestMapping("/topicos")
	public List<TopicoDTO> lista(){
		Topico topico = new Topico("Duvida", "Estou com duvida", new Curso("Spring", "Programação")); 
		return TopicoDTO.conver(Arrays.asList(topico,topico, topico));
		
	}
	
}
