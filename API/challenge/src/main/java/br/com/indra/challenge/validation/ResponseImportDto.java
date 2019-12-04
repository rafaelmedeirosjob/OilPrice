package br.com.indra.challenge.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ResponseImportDto {
	private String message;
	private Object erroRows;
}
