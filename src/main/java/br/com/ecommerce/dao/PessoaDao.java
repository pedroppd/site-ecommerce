package br.com.ecommerce.dao;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.ecommerce.domain.Pessoa;

import br.com.ecommerce.util.HibernateUtil;

public class PessoaDao extends GenericDao<Pessoa>{
	
public Pessoa autenticar(String login, String senha) {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
    try {
    	
    	Criteria consulta = sessao.createCriteria(Pessoa.class);
    	consulta.add(Restrictions.eq("login", login));

    	SimpleHash hash = new SimpleHash("md5", senha);
    	consulta.add(Restrictions.eq("senha", hash.toHex()));
    	Pessoa  resultado = (Pessoa) consulta.uniqueResult();
    	return resultado;
    	
		
	}catch(RuntimeException ex) {
		throw ex;
	}finally {
		sessao.close();
	}
    
    
}
}
