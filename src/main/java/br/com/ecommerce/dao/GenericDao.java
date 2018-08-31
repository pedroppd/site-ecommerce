package br.com.ecommerce.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.ecommerce.util.HibernateUtil;

public class GenericDao<Entidade> {

	@SuppressWarnings("unused")
	private Class<Entidade>classe;

	@SuppressWarnings("unchecked")
	public GenericDao() {
		this.classe = (Class<Entidade>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}	

//Metodo para Salvar
public void salvar(Entidade entidade) {
	
	Session sessao = HibernateUtil.getSessionFactory().openSession();
	Transaction transacao = null;
	
	try {
		
		transacao = sessao.beginTransaction();
		sessao.save(entidade);
		transacao.commit();
		
	}catch(RuntimeException ex){
		if(transacao ==null) {
			transacao.rollback();
		}
		
		throw ex;
	}finally{
		sessao.close();
	}
}


public Entidade merge(Entidade entidade) {
	
	Session sessao = HibernateUtil.getSessionFactory().openSession();
	Transaction transacao = null;
	
	try {
		
		transacao = sessao.beginTransaction();
		@SuppressWarnings("unchecked")
		Entidade retorno = (Entidade) sessao.merge(entidade);
		transacao.commit();
		return retorno;
	}catch(RuntimeException ex){
		if(transacao ==null) {
			transacao.rollback();
		}
		
		throw ex;
	}finally{
		sessao.close();
	}
	
}


//Método para listar
@SuppressWarnings("unchecked")
public List<Entidade> listar() {
	Session sessao = HibernateUtil.getSessionFactory().openSession();
	try {
		Criteria consulta = sessao.createCriteria(classe);
		List<Entidade> resultado = consulta.list();
		return resultado;
	} catch (RuntimeException erro) {
		throw erro;
	} finally {
		sessao.close();
	}
}


@SuppressWarnings("unchecked")
public List<Entidade> listar(String campoOrdenacao) {
	Session sessao = HibernateUtil.getSessionFactory().openSession();
	try {
		Criteria consulta = sessao.createCriteria(classe);
		consulta.addOrder(Order.asc(campoOrdenacao));
		List<Entidade> resultado = consulta.list();
		return resultado;
	} catch (RuntimeException erro) {
		throw erro;
	} finally {
		sessao.close();
	}
}


//Metodo para buscar pelo código
@SuppressWarnings("unchecked")
public Entidade buscar(Long codigo) {
	Session sessao = HibernateUtil.getSessionFactory().openSession();
	try {
		Criteria consulta = sessao.createCriteria(classe);
		consulta.add(Restrictions.idEq(codigo));
		Entidade resultado = (Entidade) consulta.uniqueResult();
		return resultado;
	} catch (RuntimeException erro) {
		throw erro;
	} finally {
		sessao.close();
	}
}

//Metodo para Excluir
public void excluir(Entidade entidade) {
	Session sessao = HibernateUtil.getSessionFactory().openSession();
	Transaction transacao = null;

	try {
		transacao = sessao.beginTransaction();
		sessao.delete(entidade);
		transacao.commit();
	} catch (RuntimeException erro) {
		if (transacao != null) {
			transacao.rollback();
		}
		throw erro;
	} finally {
		sessao.close();
	}
}

//Metodo para Editar
public void editar(Entidade entidade) {
	Session sessao = HibernateUtil.getSessionFactory().openSession();
	Transaction transacao = null;

	try {
		transacao = sessao.beginTransaction();
		sessao.update(entidade);
		transacao.commit();
	} catch (RuntimeException erro) {
		if (transacao != null) {
			transacao.rollback();
		}
		throw erro;
	} finally {
		sessao.close();
	}
}





}
