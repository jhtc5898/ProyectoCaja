package datos;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import modelo.Tipo_Transaccion;

@Stateless
public class TipoTransaccionDAO {

	@Inject
	private EntityManager em;
	
	public void insertarTipoTransaccion(Tipo_Transaccion tipoTransaccion) {
		em.persist(tipoTransaccion);
	}
	
	public void actualizarTipoTransaccion(Tipo_Transaccion tipoTransaccion) {
		em.merge(tipoTransaccion);
	}
	public void remove(int codigo) {
		Tipo_Transaccion tipoTransaccion = this.read(codigo);
		em.remove(tipoTransaccion);
	}
	
	public Tipo_Transaccion read(int codigo) {
		return em.find(Tipo_Transaccion.class, codigo);
	}
	
	public List<Tipo_Transaccion> getTipoTransacciones(){
		String jpql = "SELECT t FROM TipoTransaccion t";
		return em.createQuery(jpql,Tipo_Transaccion.class).getResultList();
	}
	
	public Tipo_Transaccion getTipoTransaccionNombre(String nombre){
		String jpql = "SELECT t FROM TipoTransaccion t WHERE nombre_tipo_transaccion LIKE ?1";
		Query q = em.createQuery(jpql, Tipo_Transaccion.class);
		q.setParameter(1, "%" + nombre + "%");
		Tipo_Transaccion tipoTransaccion = (Tipo_Transaccion) q.getSingleResult();
		return tipoTransaccion;
	}
}
