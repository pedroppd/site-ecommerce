package br.com.ecommerce.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Fornecedor extends GenericDomain{

	@Column(nullable=false)
	private String descricao;

	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	
}
