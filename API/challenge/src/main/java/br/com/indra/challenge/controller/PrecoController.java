package br.com.indra.challenge.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.indra.challenge.dto.preco.DetailPrecoDto;
import br.com.indra.challenge.dto.preco.PrecoDto;
import br.com.indra.challenge.model.Preco;
import br.com.indra.challenge.repository.PrecoRepository;
import br.com.indra.challenge.repository.ProdutoRepository;
import br.com.indra.challenge.request.preco.CreatePrecoRequest;
import br.com.indra.challenge.request.preco.UpdatePrecoRequest;

@RestController
@RequestMapping("/precos")
public class PrecoController {

	@Autowired
	private PrecoRepository precoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@GetMapping
	public List<PrecoDto> list() {
		List<Preco> precos = precoRepository.findAll();
		return PrecoDto.converter(precos);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<?> create(@RequestBody @Valid CreatePrecoRequest request, UriComponentsBuilder uriBuilder) {

		List<Preco> precos = precoRepository.findByAtivo(true);

		for (Preco preco : precos) {
			if(preco.getProduto().getId() == request.getProdutoId())
				preco.setAtivo(false);
		}

		Preco preco = request.converter(produtoRepository);
		precoRepository.save(preco);
		URI uri = uriBuilder.path("/precos/{id}").buildAndExpand(preco.getId()).toUri();
		return ResponseEntity.created(uri).body(new PrecoDto(preco));
	}

	@GetMapping("/{id}")
	public ResponseEntity<DetailPrecoDto> detail(@PathVariable Long id) {
		Optional<Preco> preco = precoRepository.findById(id);
		if(preco.isPresent()) {
			return ResponseEntity.ok(new DetailPrecoDto(preco.get()));
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<PrecoDto> update(@PathVariable Long id, @RequestBody @Valid UpdatePrecoRequest request){
		Optional<Preco> optional = precoRepository.findById(id);
		if(optional.isPresent()) {
			Preco preco = request.update(id, precoRepository, produtoRepository);
			return ResponseEntity.ok(new PrecoDto(preco));
		}
		return ResponseEntity.notFound().build();	
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable Long id)
	{
		Optional<Preco> optional = precoRepository.findById(id);
		if(optional.isPresent()) {
			precoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}