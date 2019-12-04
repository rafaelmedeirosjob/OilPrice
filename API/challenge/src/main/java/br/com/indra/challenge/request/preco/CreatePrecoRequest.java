package br.com.indra.challenge.request.preco;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.indra.challenge.model.Preco;
import br.com.indra.challenge.model.Produto;
import br.com.indra.challenge.repository.ProdutoRepository;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class CreatePrecoRequest {
	@NotNull
	private double valorCompra;
	private double valorVenda;
	@NotNull @NotEmpty
	private String unidadeMedida;
	@NotNull
	private LocalDateTime dataColeta;
	@NotNull
	private Long produtoId;
	
	
	public Preco converter(ProdutoRepository produtoRepository) {

		Produto produto = produtoRepository.getOne(produtoId);
		return new Preco(valorCompra, valorVenda, unidadeMedida, dataColeta, produto);
	}
}
