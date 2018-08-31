package ecommerce;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Ignore;
import org.junit.Test;

import br.com.ecommerce.dao.CidadeDao;
import br.com.ecommerce.dao.EstadoDao;
import br.com.ecommerce.dao.FornecedorDao;
import br.com.ecommerce.dao.PessoaDao;
import br.com.ecommerce.dao.ProdutoDao;
import br.com.ecommerce.domain.Cidade;
import br.com.ecommerce.domain.Estado;
import br.com.ecommerce.domain.Fornecedor;
import br.com.ecommerce.domain.Pessoa;
import br.com.ecommerce.domain.Produto;

public class DaoTeste {

	@Test
	public void salvar() {
		EstadoDao Estadodao = new EstadoDao();
		CidadeDao Cidadedao = new CidadeDao();
		
		
		Pessoa p = new Pessoa();
		Estado e = Estadodao.buscar(1L);
		Cidade c = Cidadedao.buscar(2L);

		p.setCidade(c);
		p.setCpf("555555555");
		p.setEmail("adm@hotmail.com");
		p.setLogin("pedro");
		
		
		
		p.setTipo('A');
		p.setEstado(e);
		
		PessoaDao dao = new PessoaDao();
		dao.salvar(p);
	}
	
	@Test
	@Ignore
	public void excluir() {
		FornecedorDao dao = new FornecedorDao();
		Fornecedor f = dao.buscar(1L);
		
		dao.excluir(f);
		System.out.println("Fornecedor Excluído com sucesso!!");
	}
	
	@Test
	@Ignore
	public void editar() {
		FornecedorDao dao = new FornecedorDao();
		
		Fornecedor f = new Fornecedor();
		f.setCodigo(2L);
		f.setDescricao("Teste 02");
		dao.merge(f);
		
		System.out.println("fornecedor editado com sucesso!");
	}
	
	@Test
	@Ignore
	public void listar() {
		FornecedorDao dao = new FornecedorDao();
		
		for(Fornecedor f:dao.listar()) {
			System.out.println(f.getCodigo());
			System.out.println(f.getDescricao());
		}
	}
	
	
	
	
}
