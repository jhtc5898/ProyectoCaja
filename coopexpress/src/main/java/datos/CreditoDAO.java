package datos;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import modelo.Credito;

@Stateless
public class CreditoDAO {
	
	@Inject
	private EntityManager em;
	
	public void insert(Credito credito) {
		java.util.Date fecha = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		credito.setFecha_solicitud_credito(sdf.format(fecha));
		em.persist(credito);
	}
	
	public Credito read(int codigo_credito) {
		try {
			return em.find(Credito.class, codigo_credito);
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<Credito> getCreditos(){
		String jpql = "SELECT c FROM Credito c ";
		Query q = em.createQuery(jpql);
		List<Credito> creditos =q.getResultList();
		return creditos;
	}

	public List<Credito> getCreditoCodigo() {
		String jpql = "SELECT c from Credito c ORDER BY c.codigo_credito ASC";
		Query q = em.createQuery(jpql);
		List<Credito> cred = q.getResultList();
		return cred;
	}
}