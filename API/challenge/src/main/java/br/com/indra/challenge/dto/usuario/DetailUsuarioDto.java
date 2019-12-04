package br.com.indra.challenge.dto.usuario;

import br.com.indra.challenge.model.Usuario;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class DetailUsuarioDto {
	private Long id;
	private String cpf;
	private String nome;
	
	public DetailUsuarioDto(Usuario usuario) {
		super();
		this.id = usuario.getId();
		this.cpf = usuario.getCpf();
		this.nome = usuario.getNome();
		
	}

}
