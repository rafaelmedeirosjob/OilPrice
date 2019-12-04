package br.com.indra.challenge.dto.distribuidora;
import java.util.ArrayList;
import java.util.List;

import br.com.indra.challenge.dto.bandeira.BandeiraDto;
import br.com.indra.challenge.dto.municipio.MunicipioDto;
import br.com.indra.challenge.dto.produto.ProdutoDto;
import br.com.indra.challenge.model.Distribuidora;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class DistribuidoraDto {

	private Long id;
	private String nome;
	private Long codigo;
	private BandeiraDto bandeira;
	private MunicipioDto municipio;
	private List<ProdutoDto> produtos = new ArrayList<>();


	
	public static List<DistribuidoraDto> converter(List<Distribuidora> distribuidoras) {
		List<DistribuidoraDto> usuariosDto = new ArrayList<>();
		for (Distribuidora distribuidora : distribuidoras) {
			usuariosDto.add(new DistribuidoraDto(distribuidora.getId(), distribuidora.getNome(), distribuidora.getCodigo(), new BandeiraDto(distribuidora.getBandeira()), new MunicipioDto(distribuidora.getMunicipio()),ProdutoDto.converter(distribuidora.getProdutos())));
		}
		return usuariosDto;
	}
	
	public DistribuidoraDto(Distribuidora distribuidora) {
		this.id = distribuidora.getId();
		this.nome = distribuidora.getNome();
		this.codigo = distribuidora.getCodigo();
		this.bandeira = new BandeiraDto(distribuidora.getBandeira());
		this.municipio = new MunicipioDto(distribuidora.getMunicipio());
		//this.produtos = ProdutoDto.converter(distribuidora.getProdutos());

	}
}
