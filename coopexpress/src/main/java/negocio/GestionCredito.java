package negocio;

import javax.inject.Inject;

import datos.CreditoDAO;
import datos.CuentaDAO;
import modelo.Credito;
import modelo.Cuenta;
import modelo.Usuario;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

@Stateless
public class GestionCredito {

	@Inject
	private CreditoDAO creditoDAO;
	
	@Inject
	private CuentaDAO cuentaDAO;

	public void guardar(Credito credito) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		Date fechaPagoSig = cal.getTime();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		credito.setFecha_pago_credito(sdf.format(fechaPagoSig));
		credito.setEstado_credito("P");
		creditoDAO.insert(credito);
	}
	
	public void actualizarCredito(Credito credito) {
		creditoDAO.update(credito);
	}
	public Credito getCreditoCuentaDisponible(String numeroCuenta) {
		Cuenta cuenta = cuentaDAO.getCuentaNumero(numeroCuenta);
		return creditoDAO.getCreditoCuentaDisponible(cuenta);
	}
	public Credito getCreditoCuentaControl(String numeroCuenta) {
		Cuenta cuenta = cuentaDAO.getCuentaNumero(numeroCuenta);
		return creditoDAO.getCreditoCuentaControl(cuenta);
	}
	
	public String getCreditoUnico(String numeroCuenta) {
		Cuenta cuenta =  cuentaDAO.getCuentaNumeroTransaccion(numeroCuenta);
		String unico = "";
		if(creditoDAO.getCreditoCuenta(cuenta) == null) {
			unico = "si";
		}
		return unico;
	}

	public List<Credito> getCreditos() {
		return creditoDAO.getCreditos();
	}

	public List<Credito> getCodigo() {
		return creditoDAO.getCreditoCodigo();
	}
}