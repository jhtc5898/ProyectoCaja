package datos;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import modelo.Tipo_Cuenta;

@Stateless
public class TipoCuentaDAO {

	@Inject
	private EntityManager em;
	
	public void insertarTipoCuenta(Tipo_Cuenta tipo_cuenta  ) {
		em.persist(tipo_cuenta);
	}
	
	public void update(Tipo_Cuenta tipo_cuenta) {
		em.merge(tipo_cuenta);
	}
	
	public void remove(int codigo) {
		em.remove(this.read(codigo));
	}
	
	public Tipo_Cuenta read(int codigo) {
		return em.find(Tipo_Cuenta.class, codigo);
	}
	public List<Tipo_Cuenta> getTipoCuentas(){
		String jpql = "SELECT l FROM Tipo_Cuenta l";
		Query q = em.createQuery(jpql, Tipo_Cuenta.class);
		
		List<Tipo_Cuenta> tipocuentas = q.getResultList();
		
		return tipocuentas;
	}
	
}
