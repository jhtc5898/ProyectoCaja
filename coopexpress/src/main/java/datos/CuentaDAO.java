package datos;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import modelo.Cuenta;
import modelo.Tipo_Cuenta;

@Stateless
public class CuentaDAO {

	@Inject
	private EntityManager em;
	
	
	public void insert(Cuenta cuenta) {
		cuenta.setNumero_cuenta(getLastCuenta2());
		em.persist(cuenta);
	}
	
	public String getLastCuenta() {
		Query query = em.createNativeQuery("SELECT MAX(c.codigo_cuenta) from Cuenta c");
		int lastCuenta = ((Number) query.getSingleResult()).intValue();
		return String.valueOf(lastCuenta+1);
	}
	
	public String getLastCuenta2() {
		String jpql = "SELECT c FROM Cuenta c ORDER BY c.numero_cuenta DESC";
		Query q = em.createQuery(jpql, Cuenta.class);
		List<Cuenta> cuentas =q.getResultList();
		Cuenta c =cuentas.get(0);
		int numeroCuenta = Integer.parseInt(c.getNumero_cuenta())+1;
		System.out.println(c.getNumero_cuenta());
		
		return String.valueOf(numeroCuenta);
	}
	
	public void update(Cuenta cuenta) {
		em.merge(cuenta);
	}
	
	public void remove(int codigo) {
		Cuenta cuenta  = this.read(codigo);
		em.remove(cuenta);
	}
		
	public Cuenta read(int codigo) {
		Cuenta c = em.find(Cuenta.class, codigo);
		return c;
	}

	//Obtener las cuentas para iniciar sesion
	public List<Cuenta> getCuentasLogin(){
		String jpql = "SELECT c FROM Cuenta c";
		return em.createQuery(jpql, Cuenta.class).getResultList();
	}
	
	public List<Cuenta> getCuentas(){
		String jpql = "SELECT c FROM Cuenta c WHERE tipo_cuenta_codigo != 2 AND tipo_cuenta_codigo != 4";
		return em.createQuery(jpql, Cuenta.class).getResultList();
	}
	
	public Cuenta getCuentaCorreo(String correo) {
		try {
			String jpql = "SELECT c FROM Cuenta c WHERE correo_cuenta = ?1";
			Query q = em.createQuery(jpql, Cuenta.class);
			q.setParameter(1, correo);
			Cuenta cuenta = (Cuenta)q.getSingleResult();
			return cuenta;
		} catch (Exception e) {
			return null;
		}
	}
	
	public Cuenta getCuentaNumero(String numeroCuenta){
		try {
			String jpql = "SELECT c FROM Cuenta c WHERE numero_cuenta = ?1";
			Query q = em.createQuery(jpql,Cuenta.class);
			q.setParameter(1, numeroCuenta);
			Cuenta cuenta =  (Cuenta) q.getSingleResult();
			return cuenta;
		} catch (Exception e) {
			return null;
		}
	}
	
	//Obtener cuenta solo de usuarios para la transaccion 
	public Cuenta getCuentaNumeroTransaccion(String numeroCuenta){
		try {
			String jpql = "SELECT c FROM Cuenta c WHERE numero_cuenta = ?1 AND tipo_cuenta_codigo = 1";
			Query q = em.createQuery(jpql,Cuenta.class);
			q.setParameter(1, numeroCuenta);
			Cuenta cuenta =  (Cuenta) q.getSingleResult();
			return cuenta;
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<Cuenta> listaEliminar(String numeroCuenta){
		String jpql = "SELECT c FROM Cuenta c WHERE numero_cuenta = ?1";
		Query q = em.createQuery(jpql,Cuenta.class);
		q.setParameter(1, numeroCuenta);
		List<Cuenta> cuentas = q.getResultList();
		return cuentas;
	}
	
	public List<Tipo_Cuenta> comboBox(){
		String jpql = "SELECT c FROM Tipo_Cuenta c";
		Query q = em.createQuery(jpql,Tipo_Cuenta.class);
		List<Tipo_Cuenta> tc=q.getResultList();
		return tc;
	}
	
	public String nombreTipoCuenta(int codigoTipoCuenta) {
		String jpql="SELECT TC.nombre_tipo_cuenta FROM Cuenta C, Tipo_Cuenta TC WHERE C.tipo_cuenta_codigo = TC.codigo_tipo_cuenta and TC.codigo_tipo_cuenta LIKE ?1";
		Query q = em.createQuery(jpql);
		q.setParameter(1, "%" +codigoTipoCuenta +"%");
		String rv=q.getSingleResult().toString();
		return rv;
	}
	
}
