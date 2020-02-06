package vista;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import modelo.Cuenta;
import modelo.Transaccion;
import negocio.GestionTransaccion;

@ManagedBean
@SessionScoped
public class GestionTransaccionesBean {

	@Inject
	private GestionTransaccion gt;
		
	private List<Transaccion> transaccionList = new ArrayList<Transaccion>();
	private Transaccion transaccion = new Transaccion();	
	private Cuenta cuenta;
	private String cuentaDestino;
	private String cuentaOrigen;
		
	@PostConstruct
	public void init() {
		transaccion = new Transaccion();
		cuenta = new Cuenta();
		cuentaDestino = "";
		cuentaOrigen = "";
	}
	
	//Transferencia usuario
	public String guardarDeposito() {
		String var = gt.guardarTransaccionDeposito(transaccion, cuenta, cuentaDestino);
		if(var.equals("cuenta")) {
			FacesContext.getCurrentInstance().addMessage("formulario-deposito:txtCuentaDestino", new FacesMessage("La cuenta destino no existe"));
		}else if(var.equals("dinero")) {
			FacesContext.getCurrentInstance().addMessage("formulario-deposito:txtMonto", new FacesMessage("No dispone del dinero suficiente"));
		}else if(var.equals("exito")) {
			FacesContext.getCurrentInstance().addMessage("formulario-deposito:boton", new FacesMessage("Transferencia realizada correctamente"));
			init();
		}
		init();
		transaccion.setMonto_transaccion(0);
		return null;
	}
	
	//Deposito a una cuenta CAJERA
	public String guardarDepositoCajera() {
		String var = gt.guardarTransaccionDepositoCajera(transaccion, cuentaDestino);
		if(var.equals("cuenta")) {
			FacesContext.getCurrentInstance().addMessage("formulario-deposito:txtCuentaDestino", new FacesMessage("La cuenta no existe"));
			init();
		}else if(var.equals("exito")){
			FacesContext.getCurrentInstance().addMessage("formulario-deposito:boton", new FacesMessage("Depósito realizado correctamente"));
			init();
		}
		init();
		return null;
	}
	
	//Retiro de una cuenta CAJERA
	public String guardarRetiroCajera() {
		String var = gt.guardarTransaccionRetiroCajera(transaccion, cuentaOrigen);
		if(var.equals("dinero")) {
			FacesContext.getCurrentInstance().addMessage("retiro:txtMonto", new FacesMessage("La cuenta no dispone de suficiente dinero"));
			init();
		}else if(var.equals("cuenta")) {
			FacesContext.getCurrentInstance().addMessage("retiro:txtCuentaOrigen", new FacesMessage("La cuenta no existe"));
			init();
		}else if(var.equals("exito")) {
			FacesContext.getCurrentInstance().addMessage("retiro:boton", new FacesMessage("Transaccion realizada correctamente"));
			init();
		}
		init();
		return null;
	}
	
	//Datos desde la página
	public String deposito(String cuentaOrigen) {
		if(!cuentaOrigen.equals(cuentaDestino)) {
			this.cuenta.setNumero_cuenta(cuentaOrigen);
			guardarDeposito();
		}else {
			FacesContext.getCurrentInstance().addMessage("formulario-deposito:txtCuentaDestino", new FacesMessage("No se puede realizar una transferencia hacia su propia cuenta"));
		}
		
		return null;
	}
	
	public String retiro(String cuentaOrigen) {
		this.cuenta.setNumero_cuenta(cuentaOrigen);
		guardarRetiroCajera();
		return null;
	}
	
	public String getTransacciones() {
		transaccionList = gt.getTransacciones();
		return null;
	}
	
	public void getDepositosRecibidos(String numeroCuenta) {
		transaccionList = gt.getDepositosRecibidos(numeroCuenta);
	}

	public void getDepositosRealizados(String numeroCuenta) {
		transaccionList = gt.getDepositosRealizados(numeroCuenta);
	}
	
	public void getRetirosHechos(String numeroCuenta) {
		transaccionList = gt.getRetirosHechos(numeroCuenta);
	}
	
	/*Getters y Setters*/
	public List<Transaccion> getTransaccionList() {
		return transaccionList;
	}

	public void setTransaccionList(List<Transaccion> transaccionList) {
		this.transaccionList = transaccionList;
	}

	public Transaccion getTransaccion() {
		return transaccion;
	}

	public void setTransaccion(Transaccion transaccion) {
		this.transaccion = transaccion;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public String getCuentaDestino() {
		return cuentaDestino;
	}

	public void setCuentaDestino(String cuentaDestino) {
		this.cuentaDestino = cuentaDestino;
	}

	public String getCuentaOrigen() {
		return cuentaOrigen;
	}

	public void setCuentaOrigen(String cuentaOrigen) {
		this.cuentaOrigen = cuentaOrigen;
	}
	
	
}
