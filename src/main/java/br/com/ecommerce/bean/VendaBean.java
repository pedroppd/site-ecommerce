package br.com.ecommerce.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.ecommerce.dao.PessoaDao;
import br.com.ecommerce.dao.ProdutoDao;
import br.com.ecommerce.dao.VendaDao;
import br.com.ecommerce.domain.ItemVenda;
import br.com.ecommerce.domain.Pessoa;
import br.com.ecommerce.domain.Produto;
import br.com.ecommerce.domain.Venda;



@ManagedBean
@ViewScoped
public class VendaBean {

	private List<Produto> produtos;
	private Venda venda;
	private List<Pessoa>pessoa;
	private List<ItemVenda> itensVenda;
	
	public List<Produto> getProdutos() {
		return produtos;
	}
	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	public Venda getVenda() {
		return venda;
	}
	public void setVenda(Venda venda) {
		this.venda = venda;
	}
	public List<Pessoa> getPessoa() {
		return pessoa;
	}
	public void setPessoa(List<Pessoa> pessoa) {
		this.pessoa = pessoa;
	}
	public List<ItemVenda> getItensVenda() {
		return itensVenda;
	}
	public void setItensVenda(List<ItemVenda> itensVenda) {
		this.itensVenda = itensVenda;
	}
	
	
	
	
	
	public void calcular() {
		venda.setPrecoTotal(new BigDecimal("0.00"));
		
		for(int posicao=0;posicao<itensVenda.size();posicao++) {
			ItemVenda itemVenda = itensVenda.get(posicao);		
			venda.setPrecoTotal(venda.getPrecoTotal().add(itemVenda.getPrecoParcial()));
		}
	}
	
	@PostConstruct
	public void novo() {
		try {
		venda = new Venda();
		venda.setPrecoTotal(new BigDecimal("0.00"));
		
		ProdutoDao dao = new ProdutoDao();
		produtos = dao.listar("descricao");
		
		itensVenda = new ArrayList<>();
		}catch(RuntimeException ex) {
			Messages.addGlobalError("Ocorreu um erro ao tentar carregar a tela de vendas");
			throw ex;
		}
	}
	
	
	
	
	
	public void adicionar(ActionEvent evento) {
		Produto produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");
		
		int achou = -1;
		
		for(int posicao=0; posicao<itensVenda.size();posicao++) {
			if(itensVenda.get(posicao).getProduto().equals(produto)) {
				achou = posicao;
			}
		}
		
		
		if(achou<0) {
			ItemVenda itemVenda = new ItemVenda();
			itemVenda.setProduto(produto);
			itemVenda.setQuantidade(1);
			itemVenda.setPrecoParcial(produto.getPreco());
			itensVenda.add(itemVenda);
		}else {
			ItemVenda itemVenda = itensVenda.get(achou);
			itemVenda.setQuantidade(itemVenda.getQuantidade() + 1);
			itemVenda.setPrecoParcial(produto.getPreco().multiply(new BigDecimal(itemVenda.getQuantidade())));
		}
		calcular();
	}
	
	public void remover(ActionEvent evento) {
		ItemVenda itemVenda = (ItemVenda) evento.getComponent().getAttributes().get("itemSelecionado");

		int achou = -1;
		for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
			if (itensVenda.get(posicao).getProduto().equals(itemVenda.getProduto())) {
				achou = posicao;
			}
		}

		if (achou > -1) {
			itensVenda.remove(achou);
		}

		calcular();
	}
	
	public void finalizar() {
		try {
			venda.setHorario(new Date());
			
	
		
			PessoaDao dao = new PessoaDao();
			pessoa = dao.listar();
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar finalizar a venda");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			if(venda.getPrecoTotal().signum() == 0){
				Messages.addGlobalError("Informe pelo menos um item para a venda");
				return;
			}
			
			VendaDao vendaDAO = new VendaDao();
			vendaDAO.salvar(venda, itensVenda);
			
			venda = new Venda();
			venda.setPrecoTotal(new BigDecimal("0.00"));

			ProdutoDao produtoDAO = new ProdutoDao();
			produtos = produtoDAO.listar("descricao");

			itensVenda = new ArrayList<>();
			
			Messages.addGlobalInfo("Compra Realizada Com Sucesso!!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a venda");
			erro.printStackTrace();
		}
	}
}

	
	
	
	

