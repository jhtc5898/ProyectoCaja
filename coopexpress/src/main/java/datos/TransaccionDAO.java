package datos;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import modelo.Transaccion;

@Stateless
public class TransaccionDAO {
	
	@Inject
	private EntityManager em;
	
	public boolean insertarTransaccionDeposito(Transaccion transaccion) {
		java.util.Date fecha = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		transaccion.setFecha_transaccion(sdf.format(fecha));
		em.persist(transaccion);
		
		return true;
	}
	
	public boolean insertarTransaccionRetiro(Transaccion transaccion) {
		java.util.Date fecha = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		transaccion.setFecha_transaccion(sdf.format(fecha));
		em.persist(transaccion);
		
		return true;
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
		
	public void realizarDeposito(String cuentaOrigen, String cuentaDestino, double monto) {	
		Query depositar = em.createQuery("UPDATE Cuenta c SET saldo_cuenta = saldo_cuenta + ?1 WHERE numero_cuenta = ?2");
		Query retirar = em.createQuery("UPDATE Cuenta c SET saldo_cuenta = saldo_cuenta - ?1 WHERE numero_cuenta = ?2");
		depositar.setParameter(1, monto);
		depositar.setParameter(2, cuentaDestino);
		retirar.setParameter(1, monto);
		retirar.setParameter(2, cuentaOrigen);
		depositar.executeUpdate();
		retirar.executeUpdate();
	}
	
	public void realizarRetiro(String cuentaOrigen, double monto) {	
		Query retirar = em.createQuery("UPDATE Cuenta c SET saldo_cuenta = saldo_cuenta - ?1 WHERE numero_cuenta = ?2");
		retirar.setParameter(1, monto);
		retirar.setParameter(2, cuentaOrigen);
		retirar.executeUpdate();
	}
}
