package br.com.indra.challenge.dto.estado;

import java.util.ArrayList;
import java.util.List;

import br.com.indra.challenge.dto.municipio.MunicipioDto;
import br.com.indra.challenge.model.Estado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class EstadoDto {

	private Long id;
	private String sigla;
	private String regiao;
	private List<MunicipioDto> municipios = new ArrayList<>();
	
	
	public static List<EstadoDto> converter(List<Estado> estados) {
		List<EstadoDto> estadosDto = new ArrayList<EstadoDto>();
		for (Estado estado : estados) {
			estadosDto.add(new EstadoDto(estado.getId(), estado.getSigla(), estado.getRegiao(), MunicipioDto.converter(estado.getMunicipios())));
		}
		return estadosDto;
	}

}
