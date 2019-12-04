package br.com.indra.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.indra.challenge.model.Municipio;

public interface MunicipioRepository extends JpaRepository<Municipio, Long>{
	Municipio findByNome(String nome);
}
