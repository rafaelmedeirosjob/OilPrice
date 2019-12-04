package br.com.indra.challenge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.indra.challenge.model.Produto;

public  interface ProdutoRepository extends JpaRepository<Produto, Long>{
	Produto findByNome(String nome);
	List<Produto> findByDistribuidoraMunicipioEstadoRegiao(String regiao);
	List<Produto> findByDistribuidoraCodigo(Long codigo);
}
