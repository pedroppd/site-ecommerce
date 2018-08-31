package ecommerce;

import org.junit.Test;

import br.com.ecommerce.util.HibernateUtil;

public class HibernateUtilTeste {

	@Test
	public void conectar() {
		HibernateUtil.getSessionFactory().openSession();
		System.out.println("Conectado com sucesso!!");
		HibernateUtil.getSessionFactory().close();
	}
}
