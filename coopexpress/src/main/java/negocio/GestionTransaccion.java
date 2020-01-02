package negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import datos.TransaccionDAO;
import modelo.Transaccion;

@Stateless
public class GestionTransaccion {

	@Inject
	private TransaccionDAO transaccionDAO;
	
	public void guardarTransaccionDeposito(Transaccion transaccion, String cuentaOrigen, String cuentaDestino, int tipoTransaccion) {
		if(transaccionDAO.insertarTransaccionDeposito(transaccion, cuentaOrigen, cuentaDestino, tipoTransaccion)) {
			transaccionDAO.realizarDeposito(cuentaOrigen, cuentaDestino, transaccion.getMonto_transaccion());
		}
	}
	
	public void guardarTransaccionRetiro(Transaccion transaccion, String cuentaOrigen, String cuentaDestino, int tipoTransaccion) {
		if(transaccionDAO.insertarTransaccionRetiro(transaccion, cuentaOrigen, cuentaDestino, tipoTransaccion)) {
			transaccionDAO.realizarRetiro(cuentaOrigen, transaccion.getMonto_transaccion());
		}
	}
	
	public List<Transaccion> getTransacciones() {
		return transaccionDAO.getTransacciones();
	}
	
	public List<Transaccion> getTransaccionDepositos() {
		return transaccionDAO.getTransaccioneDepositos();
	}
	
	public List<Transaccion> getTransaccionRetiros() {
		return transaccionDAO.getTransaccioneRetiros();
	}
}
