package br.com.indra.challenge.dto.produto;
import java.util.ArrayList;
import java.util.List;
import br.com.indra.challenge.dto.distribuidora.DistribuidoraDto;
import br.com.indra.challenge.dto.preco.PrecoDto;
import br.com.indra.challenge.model.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProdutoDto {
	
	private Long id;
	private String nome;
	private DistribuidoraDto distribuidora;
	private List<PrecoDto> precos = new ArrayList<>();

	
	public static List<ProdutoDto> converter(List<Produto> produtos) {
		List<ProdutoDto> produtosDto = new ArrayList<>();
		for (Produto produto : produtos) {
			produtosDto.add(new ProdutoDto(produto.getId(), produto.getNome(), new DistribuidoraDto(produto.getDistribuidora()),PrecoDto.converter(produto.getPrecos())));
		}
		return produtosDto;
	}

	public ProdutoDto(Produto produto) {
		this.id = produto.getId();
		this.nome = produto.getNome();
		this.distribuidora = new DistribuidoraDto(produto.getDistribuidora());
		this.precos = PrecoDto.converter(produto.getPrecos());

	}
}
