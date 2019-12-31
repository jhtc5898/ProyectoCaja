package negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import datos.TipoTransaccionDAO;
import datos.TransaccionDAO;
import modelo.Transaccion;

@Stateless
public class GestionTransaccion {

	@Inject
	private TransaccionDAO transaccionDAO;
	private TipoTransaccionDAO tipoTransaccionDAO;
	
	public void guardarTransaccionDeposito(Transaccion transaccion) {
		transaccionDAO.insertarTransaccionDeposito(transaccion);
	}
	
	public void guardarTransaccionRetiro(Transaccion transaccion) {
		transaccionDAO.insertarTransaccionRetiro(transaccion);
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
