package br.com.indra.challenge.request.usuario;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.indra.challenge.model.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateUsuarioRequest {
	
	@NotNull @NotEmpty
	private String nome;
	@NotNull @NotEmpty
	private String cpf;
	
	public Usuario converter() {
		return new Usuario(nome, cpf);
	}
}
