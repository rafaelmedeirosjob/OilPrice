package br.com.indra.challenge.dto.municipio;

import java.util.ArrayList;
import java.util.List;

import br.com.indra.challenge.dto.distribuidora.DistribuidoraDto;
import br.com.indra.challenge.model.Municipio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class MunicipioDto {

	
	private Long id;
	private String nome;
	private List<DistribuidoraDto> distribuidoras = new ArrayList<>();


	public static List<MunicipioDto> converter(List<Municipio> municipios) {
		List<MunicipioDto> municipiosDto = new ArrayList<>();
		for (Municipio municipio : municipios) {
			municipiosDto.add(new MunicipioDto(municipio.getId(), municipio.getNome(),DistribuidoraDto.converter(municipio.getDistribuidoras())));
		}
		return municipiosDto;
	}
	public MunicipioDto(Municipio municipio) {
		this.id = municipio.getId();
		this.nome = municipio.getNome();
	}
}
