package br.com.indra.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.indra.challenge.model.Bandeira;

public interface BandeiraRepository extends JpaRepository<Bandeira, Long>{
	Bandeira findByNome(String nome);
}
