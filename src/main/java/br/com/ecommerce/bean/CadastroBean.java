package br.com.ecommerce.bean;

import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.omnifaces.util.Messages;

import br.com.ecommerce.dao.CidadeDao;
import br.com.ecommerce.dao.EstadoDao;
import br.com.ecommerce.dao.PessoaDao;
import br.com.ecommerce.domain.Cidade;
import br.com.ecommerce.domain.Estado;
import br.com.ecommerce.domain.Pessoa;


@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CadastroBean implements Serializable{

	private Pessoa pessoa;
	private Estado estado;
	private ArrayList<Estado>estados;
	private ArrayList<Cidade>cidades;
	private ArrayList<Pessoa>pessoas;
	
	
	
	
	public Estado getEstado() {
		return estado;
	}


	public void setEstado(Estado estado) {
		this.estado = estado;
	}


	public ArrayList<Estado> getEstados() {
		return estados;
	}


	public void setEstados(ArrayList<Estado> estados) {
		this.estados = estados;
	}


	public ArrayList<Cidade> getCidades() {
		return cidades;
	}


	public void setCidades(ArrayList<Cidade> cidades) {
		this.cidades = cidades;
	}


	public ArrayList<Pessoa> getPessoas() {
		return pessoas;
	}


	public void setPessoas(ArrayList<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}


	public Pessoa getPessoa() {
		return pessoa;
	}


	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}


	

    
	public void novo() {
		pessoa = new Pessoa();
		pessoa.setCidade(new Cidade());
		pessoa.setEstado(new Estado());
		EstadoDao dao = new EstadoDao();
		estados = (ArrayList<Estado>) dao.listar();
		CidadeDao dao1 = new CidadeDao();
		cidades = (ArrayList<Cidade>) dao1.listar();
		estado = new Estado();
		
	}
	
	
	@PostConstruct
	public void listar() {
		try {
		    EstadoDao dao1 = new EstadoDao();
		    estados = (ArrayList<Estado>) dao1.listar();
		    
		    CidadeDao dao2 = new CidadeDao();
		    cidades = (ArrayList<Cidade>) dao2.listar();
			
		}catch(RuntimeException ex) {
			throw ex;
		}
	}
	
	
	
	public void cadastrar() {
		try {
			SimpleHash hash = new SimpleHash("md5", pessoa.getSenha());
			pessoa.setSenha(hash.toHex());
			
			 PessoaDao dao = new PessoaDao();
			 dao.salvar(pessoa);
			 
			 pessoa = new Pessoa();
			 pessoas = (ArrayList<Pessoa>) dao.listar();
			 
			 EstadoDao dao2 = new EstadoDao();
			 estados = (ArrayList<Estado>) dao2.listar();
			 
			 CidadeDao dao1 = new CidadeDao();
			 cidades = (ArrayList<Cidade>) dao1.listar();
	    		    
		    Messages.addGlobalInfo("Cadastrado Com sucesso!!");
		}catch(RuntimeException ex) {
			Messages.addGlobalError("Ocorreu um erro ao cadastrar :( ");
			throw ex;
		}
	}
	
	
	
			
			
	
}
