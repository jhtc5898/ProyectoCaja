package datos;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
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
		String jpql = "SELECT t FROM Tipo_Transaccion t";
		return em.createQuery(jpql,Tipo_Transaccion.class).getResultList();
	}
}
