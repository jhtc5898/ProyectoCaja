package negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import datos.CuentaDAO;
import datos.TipoTransaccionDAO;
import datos.TransaccionDAO;
import modelo.Cuenta;
import modelo.Transaccion;
import vista.MessagesBean;

@Stateless
public class GestionTransaccion {

	@Inject
	private TransaccionDAO transaccionDAO;
	
	@Inject
	private CuentaDAO cuentaDAO;
	
	@Inject
	private TipoTransaccionDAO tipoTransaccionDAO;
	
	@Inject
	private MessagesBean mensaje = new MessagesBean();
	
	public void guardarTransaccionDeposito(Transaccion transaccion, Cuenta cuentaOrigen, String ctaDestino) {
		Transaccion transaccionDest =  new Transaccion();
		Cuenta cuentaDestino = cuentaDAO.getCuentaNumeroTransaccion(ctaDestino); 
		cuentaOrigen = cuentaDAO.getCuentaNumeroTransaccion(cuentaOrigen.getNumero_cuenta());	
		if(cuentaDestino !=  null) {
			if(cuentaOrigen.getSaldo_cuenta() >= transaccion.getMonto_transaccion()) {
				//Guardar transaccion origen
				transaccion.setTipo_transaccion(tipoTransaccionDAO.read(1));
				transaccion.setDescripcion_transaccion("Deposito a la cuenta " + ctaDestino);
				transaccion.setCuenta_transaccion(cuentaOrigen);
				transaccionDAO.insertarTransaccionDeposito(transaccion);
				//Guardar transaccion destino
				transaccionDest.setMonto_transaccion(transaccion.getMonto_transaccion());
				transaccionDest.setTipo_transaccion(tipoTransaccionDAO.read(2));
				transaccionDest.setDescripcion_transaccion("Deposito recibido de" + cuentaOrigen.getNumero_cuenta());
				transaccionDest.setCuenta_transaccion(cuentaDestino);
				transaccionDAO.insertarTransaccionDeposito(transaccionDest);
				//Retiro de dinero de una cuentaOrigen y deposito en una cuentaDestino
				transaccionDAO.realizarDeposito(cuentaOrigen.getNumero_cuenta().toString(), ctaDestino, transaccion.getMonto_transaccion());
			}else {
				mensaje.setMensaje("Su cuenta no dispone de dinero suficiente para realizar la transacción");
			}
		}else {
			mensaje.setMensaje("La cuenta destino no existe");
		}

	}
	
	public void guardarTransaccionDepositoCajera(Transaccion transaccion, String ctaDestino) {
		Transaccion transaccionDest =  new Transaccion();
		Cuenta cuentaDestino = cuentaDAO.getCuentaNumeroTransaccion(ctaDestino); 
		if(cuentaDestino !=  null) {
			if(cuentaDestino.getSaldo_cuenta() >= transaccion.getMonto_transaccion()) {
				//Guardar transaccion origen
				transaccion.setTipo_transaccion(tipoTransaccionDAO.read(1));
				transaccion.setDescripcion_transaccion("Deposito MATR1 " + ctaDestino);
				transaccion.setCuenta_transaccion(cuentaDestino);
				transaccionDAO.insertarTransaccionDeposito(transaccion);
			}else {
				FacesContext.getCurrentInstance().addMessage("formulario-deposito:txtMonto", new FacesMessage("Dinero insuficiente"));
			}
		}else {
			FacesContext.getCurrentInstance().addMessage("formulario-deposito:txtCuentaDestino", new FacesMessage("La cuenta no existe"));
		}

	}
	
	public void guardarTransaccionRetiro(Transaccion transaccion, Cuenta cuentaOrigen) {
		cuentaOrigen =  cuentaDAO.getCuentaNumero(cuentaOrigen.getNumero_cuenta());
		if(cuentaOrigen.getSaldo_cuenta() >= transaccion.getMonto_transaccion()) {
			//Guardar la transaccion
			transaccion.setTipo_transaccion(tipoTransaccionDAO.read(3));
			transaccion.setDescripcion_transaccion("Retiro de la cuenta " + cuentaOrigen.getNumero_cuenta());
			transaccion.setCuenta_transaccion(cuentaOrigen);
			transaccionDAO.insertarTransaccionRetiro(transaccion);
			//Retiro de dinero de la cuenta
			transaccionDAO.realizarRetiro(cuentaOrigen.getNumero_cuenta(), transaccion.getMonto_transaccion());
		}else {
			mensaje.setMensaje("Su cuenta no dispone de dinero suficiente");
		}
	}
	
	public void guardarTransaccionRetiroCajera(Transaccion transaccion, String ctaOrigen) {
		Cuenta cuentaOrigen =  cuentaDAO.getCuentaNumero(ctaOrigen);
		if(cuentaOrigen != null) {
			if(cuentaOrigen.getSaldo_cuenta() >= transaccion.getMonto_transaccion()) {
				//Guardar la transaccion
				transaccion.setTipo_transaccion(tipoTransaccionDAO.read(3));
				transaccion.setDescripcion_transaccion("Retiro de la cuenta " + cuentaOrigen.getNumero_cuenta());
				transaccion.setCuenta_transaccion(cuentaOrigen);
				transaccionDAO.insertarTransaccionRetiro(transaccion);
				//Retiro de dinero de la cuenta
				transaccionDAO.realizarRetiro(cuentaOrigen.getNumero_cuenta(), transaccion.getMonto_transaccion());
			}else {
				FacesContext.getCurrentInstance().addMessage("formulario-deposito:txtMonto", new FacesMessage("Dinero insuficiente"));
			}
		}else {
			FacesContext.getCurrentInstance().addMessage("formulario-deposito:txtCuentaOrigen", new FacesMessage("La cuenta no existe"));
		}
	}
	
	public List<Transaccion> getTransacciones() {
		return transaccionDAO.getTransacciones();
	}
	
	public List<Transaccion> getDepositosRecibidos(String numeroCuenta){
		Cuenta cuenta = cuentaDAO.getCuentaNumero(numeroCuenta);
		return transaccionDAO.getDepositosRecibidos(cuenta);
	}
	
	public List<Transaccion> getDepositosRealizados(String numeroCuenta){
		Cuenta cuenta = cuentaDAO.getCuentaNumero(numeroCuenta);
		return transaccionDAO.getDepositosRealizados(cuenta);
	}
	
	public List<Transaccion> getRetirosHechos(String numeroCuenta){
		Cuenta cuenta = cuentaDAO.getCuentaNumero(numeroCuenta);
		return transaccionDAO.getRetirosHechos(cuenta);
	}
	
	public List<Transaccion> getTransaccionDepositos() {
		return transaccionDAO.getTransaccionesDepositos();
	}
	
	public List<Transaccion> getTransaccionRetiros() {
		return transaccionDAO.getTransaccionesRetiros();
	}
}
