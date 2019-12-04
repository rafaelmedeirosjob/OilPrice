package br.com.indra.challenge.request.preco;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

import br.com.indra.challenge.model.Preco;
import br.com.indra.challenge.model.Produto;
import br.com.indra.challenge.repository.PrecoRepository;
import br.com.indra.challenge.repository.ProdutoRepository;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdatePrecoRequest {
	@NotNull
	private double valorCompra;
	private double valorVenda;
	@NotNull 
	private LocalDateTime dataColeta;
	@NotNull 
	private Long produtoId;
	
	public Preco update(Long id, PrecoRepository precoRepository, ProdutoRepository produtoRepository) {
		Preco preco = precoRepository.getOne(id);
		Produto produto = produtoRepository.getOne(produtoId);
		preco.setValorCompra(this.valorCompra);
		preco.setProduto(produto);
		
		return preco;
	}

}
