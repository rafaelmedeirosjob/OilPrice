package br.com.indra.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.indra.challenge.model.Distribuidora;

public  interface DistribuidoraRepository extends JpaRepository<Distribuidora, Long>{
	
	Distribuidora findByCodigo(Long codigo);
}
