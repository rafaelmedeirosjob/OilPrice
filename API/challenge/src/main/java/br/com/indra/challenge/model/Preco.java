package br.com.indra.challenge.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Preco {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private double valorCompra;
	private double valorVenda;
	private boolean ativo = true;
	private String unidadeMedida;
	private LocalDateTime dataColeta;
	@ManyToOne
	private Produto produto;
	
	public Preco(double valorCompra, double valorVenda, String unidadeMedida, LocalDateTime dataColeta, Produto produto) {
		this.valorCompra = valorCompra;
		this.unidadeMedida = unidadeMedida;
		this.valorVenda = valorVenda;
		this.dataColeta = dataColeta;
		this.produto = produto;
	}
}
