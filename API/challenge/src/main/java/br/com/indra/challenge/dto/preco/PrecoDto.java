package br.com.indra.challenge.dto.preco;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.indra.challenge.dto.produto.ProdutoDto;
import br.com.indra.challenge.model.Preco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class PrecoDto {
	private Long id;
	private double valorCompra;
	private double valorVenda;
	private String unidadeMedida;
	private LocalDateTime dataColeta;
	private ProdutoDto produto;
	
	public static List<PrecoDto> converter(List<Preco> precos) {
		List<PrecoDto> precosDto = new ArrayList<PrecoDto>();
		for (Preco preco : precos) {
			precosDto.add(new PrecoDto(preco.getId(), preco.getValorCompra(),preco.getValorVenda(),preco.getUnidadeMedida(), preco.getDataColeta(), new ProdutoDto(preco.getProduto())));
		}
		return precosDto;
	}
	
	public PrecoDto(Preco preco) {
		this.id = preco.getId();
		this.valorCompra = preco.getValorCompra();
		this.valorVenda = preco.getValorVenda();
		this.unidadeMedida = preco.getUnidadeMedida();
		this.dataColeta = preco.getDataColeta();
		this.produto = new ProdutoDto(preco.getProduto());
	}
}
