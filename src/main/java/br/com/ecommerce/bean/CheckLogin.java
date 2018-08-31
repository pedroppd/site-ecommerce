package br.com.ecommerce.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.com.ecommerce.dao.PessoaDao;
import br.com.ecommerce.dao.ProdutoDao;
import br.com.ecommerce.dao.VendaDao;
import br.com.ecommerce.domain.ItemVenda;
import br.com.ecommerce.domain.Pessoa;
import br.com.ecommerce.domain.Produto;
import br.com.ecommerce.domain.Venda;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class CheckLogin implements Serializable {

	private Pessoa pessoa;
	private Pessoa logado;
	private Venda venda;
	private List<Produto> produtos;
	private List<Pessoa> pessoas;
	private List<ItemVenda> itensVenda;

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public List<ItemVenda> getItensVenda() {
		return itensVenda;
	}

	public void setItensVenda(List<ItemVenda> itensVenda) {
		this.itensVenda = itensVenda;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Pessoa getLogado() {
		return logado;
	}

	public void setLogado(Pessoa logado) {
		this.logado = logado;
	}

	public void novo1() {
		pessoa = new Pessoa();
	}

	public void autenticar() throws IOException {

		try {
			PessoaDao dao = new PessoaDao();
			logado = dao.autenticar(pessoa.getLogin(), pessoa.getSenha());

			if (logado == null) {
				Messages.addFlashGlobalWarn("Login ou senha incorretos !");
			} else {
				Faces.redirect("./pages/homeLogado.xhtml");
			}

		} catch (IOException ex) {
			throw ex;
		}
	}

	public void finalizar() {
		try {
			venda.setHorario(new Date());
			venda.setPessoa(logado.getCodigo());

		} catch (RuntimeException erro) {

			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			if (venda.getPrecoTotal().signum() == 0) {
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

	public void calcular() {
		venda.setPrecoTotal(new BigDecimal("0.00"));

		for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
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
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Ocorreu um erro ao tentar carregar a tela de vendas");
			throw ex;
		}
	}

	public void adicionar(ActionEvent evento) {
		Produto produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");

		int achou = -1;

		for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
			if (itensVenda.get(posicao).getProduto().equals(produto)) {
				achou = posicao;
			}
		}

		if (achou < 0) {
			ItemVenda itemVenda = new ItemVenda();
			itemVenda.setProduto(produto);
			itemVenda.setQuantidade(1);
			itemVenda.setPrecoParcial(produto.getPreco());
			itensVenda.add(itemVenda);
		} else {
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

	public boolean temPermissoes(ArrayList<String> permissoes) {
		for (String permissao : permissoes) {
			if (logado.getTipo()== permissao.charAt(0)) {
				return true;
			}
		}
		return false;
	}

}
