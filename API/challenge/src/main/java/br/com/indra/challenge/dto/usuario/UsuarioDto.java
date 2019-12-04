package br.com.indra.challenge.dto.usuario;

import org.springframework.data.domain.Page;

import br.com.indra.challenge.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class UsuarioDto {

	private Long id;
	private String cpf;
	private String nome;

	public static Page<UsuarioDto> converter(Page<Usuario> usuarios) {
		return usuarios.map(UsuarioDto::new);
	}


	public UsuarioDto(Usuario usuario) {
		this.id = usuario.getId();
		this.cpf = usuario.getCpf();
		this.nome = usuario.getNome();
	}


}
