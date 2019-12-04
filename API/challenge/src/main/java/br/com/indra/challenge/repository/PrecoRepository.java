package br.com.indra.challenge.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.indra.challenge.model.Preco;

public interface PrecoRepository extends JpaRepository<Preco, Long>{
	List<Preco> findByAtivo(boolean ativo);
	List<Preco> findByDataColeta(LocalDateTime dataColeta);
	@Query("SELECT AVG((p.valorCompra + p.valorVenda)/2) as media FROM Preco as p WHERE p.produto.distribuidora.municipio.nome = :municipio")
	double avgValorCompraValorVendaPorMunicipio(@Param("municipio") String municipio); 
	
	@Query("SELECT AVG((p.valorCompra + p.valorVenda)/2) as media FROM Preco as p WHERE p.produto.distribuidora.bandeira.nome = :bandeira")
	double avgValorCompraValorVendaPorBandeira(@Param("bandeira") String bandeira); 
}
