package negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import datos.CuentaDAO;
import datos.TipoCuentaDAO;
import datos.UsuarioDAO;
import modelo.Cuenta;
import modelo.Tipo_Cuenta;

@Stateless
public class GestionCuentas {

	@Inject
	private CuentaDAO cuentadDAO;
	
	@Inject
	private UsuarioDAO usuarioDAO;
	
	@Inject
	private TipoCuentaDAO tipoCuentaDAO;
	
	public String guardar(Cuenta cuenta) {
		String resultado = "";
		if (cuentadDAO.getCuentaCorreo(cuenta.getCorreo_cuenta()) == null) {
			usuarioDAO.habilitarUsuario(cuenta.getUsuario().getCedula());
			cuentadDAO.insert(cuenta);
		}else {
			resultado = null;
		}
		return resultado;
	}
	
	public String guardarUsuario(Cuenta cuenta) {
		String resultado = "";
		if (cuentadDAO.getCuentaCorreo(cuenta.getCorreo_cuenta()) == null) {
			cuenta.setSaldo_cuenta(0);
			cuenta.setTipo_cuenta(tipoCuentaDAO.read(1));
			cuenta.setNumero_cuenta("10000001");
			usuarioDAO.habilitarUsuario(cuenta.getUsuario().getCedula());
			cuentadDAO.insert(cuenta);
		}else {
			resultado = null;
		}
		return resultado;
	}
	
	public String guardarCajero(Cuenta cuenta) {
		String resultado = "";
		if (cuentadDAO.getCuentaCorreo(cuenta.getCorreo_cuenta()) == null) {
			cuenta.setSaldo_cuenta(0);
			cuenta.setTipo_cuenta(tipoCuentaDAO.read(3));
			cuenta.setNumero_cuenta("10000001");
			cuentadDAO.insert(cuenta);
		}else {
			resultado = null;
		}
		return resultado;
	}
	
	public void actualizar(Cuenta cuenta) {
		cuentadDAO.update(cuenta);
	}
	
	public void actualizarEliminado(Cuenta cuenta) {
		cuenta.setTipo_cuenta(tipoCuentaDAO.read(4));
		cuentadDAO.update(cuenta);
	}
	
	public void eliminar(Cuenta cuenta) {
		cuenta.setTipo_cuenta(tipoCuentaDAO.read(4));
		cuentadDAO.update(cuenta);
	}
			
	public Cuenta getCuentaCorreo(String correo) {
		return cuentadDAO.getCuentaCorreo(correo);
	}
	
	public List<Cuenta> getCuentasLogin(){
		return cuentadDAO.getCuentasLogin();
	}
	
	public List<Cuenta> getCuentas(){
		return cuentadDAO.getCuentas();
	}
	
	public Cuenta obtenerCuentaNumero(String numeroCuenta) {
		return cuentadDAO.getCuentaNumero(numeroCuenta);
	}
	
	public Cuenta obtenerCuentaNumeroCredito(String numeroCuenta) {
		return cuentadDAO.getCuentaNumeroTransaccion(numeroCuenta);
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
