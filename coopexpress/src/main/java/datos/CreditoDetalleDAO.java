package datos;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import modelo.Credito;
import modelo.Credito_Detalle;

@Stateless
public class CreditoDetalleDAO {

	@Inject
	private EntityManager em;
		
	private CreditoDAO creditoDAO;
	
	public void insert(Credito_Detalle detalle) {
		em.persist(detalle);
	}
	
	public List<Credito> getCreditoDetalleCodigo() {
		String jpql = "SELECT c from Credito_Detalle c ORDER BY c.codigo_credito DESC";
		Query q = em.createQuery(jpql);
		List<Credito> cred = q.getResultList();
		return cred;
	}
	
	public List<Credito_Detalle> getCreditoDetalle(int codigo_credito) {
		String jpql = "SELECT c FROM Credito_Detalle c WHERE codigo_credito = ?1";
		Query q = em.createQuery(jpql,Credito_Detalle.class);
		Credito credito = creditoDAO.read(codigo_credito);
		q.setParameter(1, credito);
		List<Credito_Detalle> creditoDetalles = q.getResultList();
		return creditoDetalles;
	}
	
	public List<Credito_Detalle> getPagosCuenta(String numeroCuenta) {
		String jpql = "SELECT cd FROM Credito_Detalle cd, Credito cr, Cuenta cu WHERE ?1 = cr.codigo_cuenta AND cr.codigo_credito = cd.codigo_credito";
		Query q = em.createQuery(jpql,Credito_Detalle.class);
		q.setParameter(1, numeroCuenta);
		List<Credito_Detalle> creditoDetalles = q.getResultList();
		return creditoDetalles;
	}
}
