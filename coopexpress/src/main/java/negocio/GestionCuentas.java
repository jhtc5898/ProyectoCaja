package negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import datos.CuentaDAO;
import modelo.Cuenta;
import modelo.Tipo_Cuenta;

@Stateless
public class GestionCuentas {

	@Inject
	private CuentaDAO cuentadDAO;
	
	public void guardar(Cuenta cuenta) {
		cuentadDAO.insert(cuenta);
	}
	
	public void actualizar(Cuenta cuenta) {
		cuentadDAO.update(cuenta);
	}
	
	public void eliminar(int codigo) {
		cuentadDAO.remove(codigo);
	}
	
	public Cuenta getCuentaCorreo(String correo) {
		return cuentadDAO.getCuentaCorreo(correo);
	}
	
	public List<Cuenta> getCuentas(){
		return cuentadDAO.getCuentas();
	}
	
	public Cuenta obtenerCuentaNumero(String numeroCuenta) {
		return cuentadDAO.getCuentaNumero(numeroCuenta);
	}
	
	public List<Cuenta> listaEliminar(String numeroCuenta){
		return cuentadDAO.listaEliminar(numeroCuenta);
	}
	
	public List<Tipo_Cuenta> comboBox(){
		return cuentadDAO.comboBox();
	}
	
	public String nombreTipoCuenta(int codigoTipoCuenta) {
		return cuentadDAO.nombreTipoCuenta(codigoTipoCuenta);
	}
	
}
