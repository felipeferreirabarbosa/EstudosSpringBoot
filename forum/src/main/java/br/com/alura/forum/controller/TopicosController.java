package br.com.alura.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired
	TopicoRepository topicoRepository;
	
	@Autowired
	CursoRepository cursoRepository;

	@GetMapping
	public List<TopicoDTO> lista(String nomeCurso){ 
		List<Topico> topicos;
		if(nomeCurso==null) {
			 topicos = topicoRepository.findAll();
		} else {
			 topicos = topicoRepository.findByCurso_Nome(nomeCurso);
		}
		return TopicoDTO.conver(topicos);
		
	}
	
	@PostMapping
	public void cadastrar(@RequestBody TopicoForm form){ 
		Topico topico = form.convert(cursoRepository);
		topicoRepository.save(topico);	
	}
	
}
