package datos;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import modelo.Transaccion;

@Stateless
public class TransaccionDAO {
	
	@Inject
	private EntityManager em;
	
	public void insertarTransaccionDeposito(Transaccion transaccion) {
		em.persist(transaccion);
	}
	
	public void insertarTransaccionRetiro(Transaccion transaccion) {
		em.persist(transaccion);
	}
	
	public void actualizarTransaccion(Transaccion transaccion) {
		em.merge(transaccion);
	}
	public void remove(int codigo) {
		Transaccion transaccion = this.read(codigo);
		em.remove(transaccion);
	}
	
	public Transaccion read(int codigo) {
		return em.find(Transaccion.class, codigo);
	}
	
	public List<Transaccion> getTransacciones(){
		String jpql = "SELECT t FROM Transaccion t";
		return em.createQuery(jpql,Transaccion.class).getResultList();
	}
	
	public List<Transaccion> getTransaccioneDepositos(){
		String jpql = "SELECT t FROM Transaccion t WHERE tipo_transaccion = 1";
		return em.createQuery(jpql,Transaccion.class).getResultList();
	}
	
	public List<Transaccion> getTransaccioneRetiros(){
		String jpql = "SELECT t FROM Transaccion t WHERE tipo_transaccion = 2";
		return em.createQuery(jpql,Transaccion.class).getResultList();
	}
}
