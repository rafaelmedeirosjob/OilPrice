package br.com.indra.challenge.dto.bandeira;

import br.com.indra.challenge.model.Bandeira;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class BandeiraDto {

	private Long id;
	private String nome;
	
	public BandeiraDto(Bandeira bandeira) {
		this.id = bandeira.getId();
		this.nome = bandeira.getNome();
	}
}
