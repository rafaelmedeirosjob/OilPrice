package br.com.indra.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.indra.challenge.model.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long>{
	Estado findBySigla(String sigla);
	Estado findByRegiao(String regiao);
}
