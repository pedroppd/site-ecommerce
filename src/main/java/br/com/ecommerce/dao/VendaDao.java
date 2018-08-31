package br.com.ecommerce.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.omnifaces.util.Messages;

import br.com.ecommerce.domain.Venda;
import br.com.ecommerce.domain.ItemVenda;
import br.com.ecommerce.domain.Produto;
import br.com.ecommerce.util.HibernateUtil;

public class VendaDao extends GenericDao<Venda>{

	public void salvar(Venda venda, List<ItemVenda> itensVenda){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
		
			sessao.save(venda);
			
			for(int posicao = 0; posicao < itensVenda.size(); posicao++){
				ItemVenda itemVenda = itensVenda.get(posicao);
				itemVenda.setVenda(venda);
				sessao.save(itemVenda);		
				Produto produto = itemVenda.getProduto();
			
			}
			
			
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
