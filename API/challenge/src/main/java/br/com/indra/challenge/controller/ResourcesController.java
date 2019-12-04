package br.com.indra.challenge.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.indra.challenge.dto.preco.PrecoDto;
import br.com.indra.challenge.dto.produto.ProdutoDto;
import br.com.indra.challenge.model.Distribuidora;
import br.com.indra.challenge.model.Municipio;
import br.com.indra.challenge.model.Preco;
import br.com.indra.challenge.model.Produto;
import br.com.indra.challenge.repository.MunicipioRepository;
import br.com.indra.challenge.repository.PrecoRepository;
import br.com.indra.challenge.repository.ProdutoRepository;

@RestController
@RequestMapping("/querys")
public class ResourcesController {
	
	@Autowired
	private PrecoRepository precoRepository;
	
	@Autowired
	private MunicipioRepository municipioRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	//só pra ver a diferença da forma mais trabalhosa
	@GetMapping("/precos/avg/municipios")
	public ResponseEntity<String> avgPrecoForMunicipio(@RequestParam(required=true) String nome) {
		float total = 0;
		float quantidade = 0;
		nome = nome.toUpperCase();
		Municipio municipio = municipioRepository.findByNome(nome);
		if(municipio != null) {
			for (Distribuidora distribuidora : municipio.getDistribuidoras()) {
				for (Produto produto : distribuidora.getProdutos()) {
					for (Preco preco : produto.getPrecos()) {
						total += preco.getValorCompra();
						quantidade++;
					}
				}
			}
			return new ResponseEntity<>(String.valueOf(total/quantidade) , HttpStatus.OK);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/allInfos/siglas")
	public ResponseEntity<List<ProdutoDto>> allInfoForSigla(@RequestParam(required=true) String sigla) {
		return ResponseEntity.ok(ProdutoDto.converter(produtoRepository.findByDistribuidoraMunicipioEstadoRegiao(sigla)));
	}
	
	@GetMapping("/allInfos/distribuidoras")
	public ResponseEntity<List<ProdutoDto>> allInfosGroupedDistribuidora(@RequestParam(required=true) String distribuidora) {
		return ResponseEntity.ok(ProdutoDto.converter(produtoRepository.findByDistribuidoraCodigo(Long.valueOf(distribuidora))));
	}
	
	@GetMapping("/allInfos/dataColeta")
	public ResponseEntity<List<PrecoDto>> allInfosGroupedDataColeta(@RequestParam(required=true) String dataColeta) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(dataColeta, formatter);
		return ResponseEntity.ok(PrecoDto.converter(precoRepository.findByDataColeta(dateTime)));
	}
	// Consulta AVG otimizada
	@GetMapping("/municipios/avg/valorCompra/valorVenda")
	public ResponseEntity<?> avgValorCompraAndValorVendaForMunicipio(@RequestParam(required=true) String municipio) {
		municipio = municipio.toUpperCase();
		return ResponseEntity.ok(precoRepository.avgValorCompraValorVendaPorMunicipio(municipio));
	}
	// Consulta AVG otimizada
	@GetMapping("/bandeiras/avg/valorCompra/valorVenda")
	public ResponseEntity<?> avgValorCompraAndValorVendaForBandeira(String bandeira) {
		bandeira = bandeira.toUpperCase();
		return ResponseEntity.ok(precoRepository.avgValorCompraValorVendaPorBandeira(bandeira));
	}

}
