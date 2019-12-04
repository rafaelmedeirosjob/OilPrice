package br.com.indra.challenge.dto.preco;

import java.time.LocalDateTime;

import br.com.indra.challenge.model.Preco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class DetailPrecoDto {
	
	private Long id;
	private double valor;
	private double valorVenda;
	private boolean ativo;
	private String unidadeMedida;
	private LocalDateTime dataColeta;
	private String produtoNome;
	
	public DetailPrecoDto(Preco preco)
	{
		super();
		this.id = preco.getId();
		this.valor = preco.getValorCompra();
		this.valorVenda = preco.getValorVenda();
		this.ativo = preco.isAtivo();
		this.unidadeMedida = preco.getUnidadeMedida();
		this.dataColeta = preco.getDataColeta();
		this.produtoNome = preco.getProduto().getNome();
	}

}
