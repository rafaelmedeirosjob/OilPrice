package br.com.indra.challenge.request.usuario;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.indra.challenge.model.Usuario;
import br.com.indra.challenge.repository.UsuarioRepository;


public class UpdateUsuarioRequest {
	
	@NotNull @NotEmpty
	private String cpf;
	@NotNull @NotEmpty
	private String nome;
	
	public Usuario atualizar(Long id, UsuarioRepository usuarioRepository) {
		Usuario usuario = new Usuario();
		usuario = usuarioRepository.getOne(id);
		usuario.setCpf(this.cpf);
		usuario.setNome(this.nome);
		
		return usuario;
	}

}
