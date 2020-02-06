package datos;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import modelo.Credito;
import modelo.Cuenta;
import modelo.Tipo_Transaccion;
import modelo.Transaccion;

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

	public void update(Credito credito) {
		em.merge(credito);
	}
	
	public Credito read(int codigo_credito) {
		try {
			return em.find(Credito.class, codigo_credito);
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<Credito> getCreditos(){
		String jpql = "SELECT c FROM Credito c WHERE estado_credito <> 'H' AND estado_credito <> ''";
		Query q = em.createQuery(jpql);
		List<Credito> creditos =q.getResultList();
		return creditos;
	}
	
	public Credito getCreditoCuenta(Cuenta numeroCuenta) {
		try {
			String jpql = "SELECT c FROM Credito c WHERE codigo_cuenta = ?1";
			Query q = em.createQuery(jpql, Credito.class);
			q.setParameter(1, numeroCuenta);
			Credito credito = (Credito) q.getSingleResult();
			return credito;
		} catch (Exception e) {
			return null;
		}
	}
	
	public Credito getCreditoCuentaDisponible(Cuenta numeroCuenta) {
		try {
			String jpql = "SELECT c FROM Credito c WHERE codigo_cuenta = ?1 and estado_credito <> 'P'";
			Query q = em.createQuery(jpql, Credito.class);
			q.setParameter(1, numeroCuenta);
			Credito credito = (Credito) q.getSingleResult();
			return credito;
		} catch (Exception e) {
			return null;
		}
		
	}
	
	public Credito getCreditoCuentaControl(Cuenta numeroCuenta) {
		try {
			String jpql = "SELECT c FROM Credito c WHERE codigo_cuenta = ?1 and estado_credito = 'P'";
			Query q = em.createQuery(jpql, Credito.class);
			q.setParameter(1, numeroCuenta);
			Credito credito = (Credito) q.getSingleResult();
			return credito;
		} catch (Exception e) {
			return null;
		}
		
	}
	

	public List<Credito> getCreditoCodigo() {
		String jpql = "SELECT c from Credito c ORDER BY c.codigo_credito ASC";
		Query q = em.createQuery(jpql);
		List<Credito> cred = q.getResultList();
		return cred;
	}
}
