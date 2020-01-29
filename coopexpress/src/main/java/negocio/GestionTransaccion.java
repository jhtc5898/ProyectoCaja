package negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import datos.CuentaDAO;
import datos.TipoTransaccionDAO;
import datos.TransaccionDAO;
import modelo.Cuenta;
import modelo.Tipo_Transaccion;
import modelo.Transaccion;

@Stateless
public class GestionTransaccion {

	@Inject
	private TransaccionDAO transaccionDAO;
	
	@Inject
	private CuentaDAO cuentaDAO;
	
	@Inject
	private TipoTransaccionDAO tipoTransaccionDAO;
	
	public void guardarTransaccionDeposito(Transaccion transaccion, Cuenta cuentaOrigen, String ctaDestino) {
		Transaccion transaccionDest =  new Transaccion();
		Cuenta cuentaDestino = cuentaDAO.getCuentaNumero(ctaDestino); 
		cuentaOrigen = cuentaDAO.getCuentaNumero(cuentaOrigen.getNumero_cuenta());		
		if(cuentaDestino !=  null) {
			if(cuentaOrigen.getSaldo_cuenta() >= transaccion.getMonto_transaccion()) {
				//Guardar transaccion origen
				transaccion.setTipo_transaccion(tipoTransaccionDAO.read(1));
				transaccion.setDescripcion_transaccion("Deposito realizado a la cuenta " + ctaDestino);
				transaccion.setCuenta_transaccion(cuentaOrigen);
				transaccionDAO.insertarTransaccionDeposito(transaccion);
				//Guardar transaccion destino
				transaccionDest.setMonto_transaccion(transaccion.getMonto_transaccion());
				transaccionDest.setTipo_transaccion(tipoTransaccionDAO.read(2));
				transaccionDest.setDescripcion_transaccion("Deposito realizado desde la cuenta " + cuentaOrigen.getNumero_cuenta());
				transaccionDest.setCuenta_transaccion(cuentaDestino);
				transaccionDAO.insertarTransaccionDeposito(transaccionDest);
				transaccionDAO.realizarDeposito(cuentaOrigen.getNumero_cuenta().toString(), ctaDestino, transaccion.getMonto_transaccion());
			}
		}
	}
	
	
	
	
//	public void guardarTransaccionRetiro(Transaccion transaccion, String cuentaOrigen, String cuentaDestino, int tipoTransaccion) {
//		if(transaccionDAO.insertarTransaccionRetiro(transaccion, cuentaOrigen, cuentaDestino, tipoTransaccion)) {
//			transaccionDAO.realizarRetiro(cuentaOrigen, transaccion.getMonto_transaccion());
//		}
//	}
	
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
