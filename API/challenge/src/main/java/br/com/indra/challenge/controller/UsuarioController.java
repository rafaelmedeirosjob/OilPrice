package br.com.indra.challenge.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.indra.challenge.dto.usuario.DetailUsuarioDto;
import br.com.indra.challenge.dto.usuario.UsuarioDto;
import br.com.indra.challenge.model.Usuario;
import br.com.indra.challenge.repository.UsuarioRepository;
import br.com.indra.challenge.request.usuario.CreateUsuarioRequest;
import br.com.indra.challenge.request.usuario.UpdateUsuarioRequest;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	//usando paginação
	@GetMapping
	public ResponseEntity<Page<UsuarioDto>> list(@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao) {
		Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);
			return ResponseEntity.ok().body(UsuarioDto.converter(usuarios));
	}
	@PostMapping
	@Transactional
	public ResponseEntity<UsuarioDto> create(@RequestBody @Valid CreateUsuarioRequest request, UriComponentsBuilder uriBuilder) {
		Usuario usuario = request.converter();
		usuarioRepository.save(usuario);
		URI uri = uriBuilder.path("/usurios/{id}").buildAndExpand(usuario.getId()).toUri();
		return ResponseEntity.created(uri).body(new UsuarioDto(usuario));
	}

	@GetMapping("/{id}")
	public ResponseEntity<DetailUsuarioDto> detail(@PathVariable Long id) {
		Optional<Usuario> optional = usuarioRepository.findById(id);
		if(optional.isPresent()) {
			return ResponseEntity.ok(new DetailUsuarioDto(optional.get()));
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<UsuarioDto> update(@PathVariable Long id, @RequestBody @Valid UpdateUsuarioRequest form){
		Optional<Usuario> optional = usuarioRepository.findById(id);
		if(optional.isPresent()) {
			Usuario usuario = form.atualizar(id, usuarioRepository);
			return ResponseEntity.ok(new UsuarioDto(usuario));
		}
		return ResponseEntity.notFound().build();	
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable Long id)
	{
		Optional<Usuario> optional = usuarioRepository.findById(id);
		if(optional.isPresent()) {
			usuarioRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

}
