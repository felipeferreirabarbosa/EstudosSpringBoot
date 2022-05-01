package br.com.alura.forum.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalhesTopicoDTO;
import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
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
	@Cacheable(value="listaDeTopicos")
	public Page<TopicoDTO> lista(@RequestParam(required = false) String nomeCurso,@PageableDefault(sort = "id", direction = Direction.DESC) Pageable paginacao){ 
		Page<Topico> topicos;
		
		if(nomeCurso==null) {
			 topicos = topicoRepository.findAll(paginacao);
		} else {
			 topicos = topicoRepository.findByCurso_Nome(nomeCurso, paginacao);
		}
		return TopicoDTO.conver(topicos);
		
	}
	
	/* Código 201 HTTP (CREATED): Sucesso e recurso criado no servidor */
	@PostMapping
	@Transactional
	public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder){ 
		Topico topico = form.convert(cursoRepository);
		topicoRepository.save(topico);	
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDTO(topico));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesTopicoDTO> detalhar(@PathVariable Long id) {
		Optional<Topico> optionalTopico = topicoRepository.findById(id);
		if(optionalTopico.isPresent()) {
			DetalhesTopicoDTO detalheTopicoDTO = new DetalhesTopicoDTO(optionalTopico.get());
			return ResponseEntity.ok(detalheTopicoDTO);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form){ 
		Optional<Topico> optionalTopico = topicoRepository.findById(id);
		if(optionalTopico.isPresent()) {
			Topico topico = form.atualizar(id,topicoRepository);
			return ResponseEntity.ok(new TopicoDTO(topico));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id){ 
		Optional<Topico> optionalTopico = topicoRepository.findById(id);
		if(optionalTopico.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
