package vista;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
	
	public String guardarDeposito() {
		gt.guardarTransaccionDeposito(transaccion, cuenta, cuentaDestino);
		init();
		return null;
	}
	public String guardarDepositoCajera() {
		gt.guardarTransaccionDepositoCajera(transaccion, cuentaDestino);
		init();
		return null;
	}
	
	public String guardarRetiro() {
		gt.guardarTransaccionRetiroCajera(transaccion, cuentaOrigen);
		init();
		return null;
	}
	
	//Datos desde la página
	public String deposito(String cuentaOrigen) {
		this.cuenta.setNumero_cuenta(cuentaOrigen);
		guardarDeposito();
		return null;
	}
	
	public String retiro(String cuentaOrigen) {
		this.cuenta.setNumero_cuenta(cuentaOrigen);
		guardarRetiro();
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
