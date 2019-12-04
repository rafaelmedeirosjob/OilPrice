package br.com.indra.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.indra.challenge.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
