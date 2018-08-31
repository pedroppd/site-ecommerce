package br.com.ecommerce.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.ecommerce.dao.ProdutoDao;
import br.com.ecommerce.domain.Produto;

@ManagedBean
@ViewScoped
public class CursoDeslogadoBean {
private Produto produto;
	
	private List<Produto>produtos;
	
	
	
	
	public Produto getProduto() {
		return produto;
	}




	public void setProduto(Produto produto) {
		this.produto = produto;
	}




	public List<Produto> getProdutos() {
		return produtos;
	}




	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}




	@PostConstruct
	public void listar() {
		ProdutoDao dao = new ProdutoDao();
		produtos = dao.listar();
	}
	
	
	public void comprar() {
		try {
			Messages.addGlobalWarn("Você precisa estar logado!");
			
		}catch(RuntimeException ex) {
			throw ex;
		}
	}
}
