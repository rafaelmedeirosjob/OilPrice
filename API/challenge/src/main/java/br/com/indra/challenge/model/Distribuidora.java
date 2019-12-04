package br.com.indra.challenge.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Distribuidora {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private Long codigo;
	@ManyToOne
	private Municipio municipio;
	@OneToMany(mappedBy = "distribuidora")
	private List<Produto> produtos = new ArrayList<>();
	@ManyToOne
	private Bandeira bandeira;

}
