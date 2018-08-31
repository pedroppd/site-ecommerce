package br.com.ecommerce.domain;

import javax.persistence.Column;
import javax.persistence.Entity;


@Entity
public class Estado extends GenericDomain{

	@Column(nullable=false, length=20)
	private String nome;
	
	@Column(nullable=false, length=2)
	private String sigla;

	
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	
	
	
	
	
	
}
