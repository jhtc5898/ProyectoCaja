package vista;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import modelo.Transaccion;
import negocio.GestionTransaccion;

@ManagedBean
@SessionScoped
public class GestionTransaccionesBean {

	@Inject
	private GestionTransaccion gt;
	
	private List<Transaccion> transaccionList = new ArrayList<Transaccion>();
	private Transaccion transaccion = new Transaccion();	
	private String cuentaOrigen;
	private String cuentaDestino;
	
	@PostConstruct
	public void init() {
		transaccion = new Transaccion();
		cuentaOrigen = "";
		cuentaDestino = "";
	}
	
	public String guardarDeposito() {
		gt.guardarTransaccionDeposito(transaccion, cuentaOrigen, cuentaDestino, 1);
		init();
		return null;
	}
	
	public String guardarRetiro() {
		gt.guardarTransaccionRetiro(transaccion, cuentaOrigen, cuentaDestino, 2);
		init();
		return null;
	}
	
	public String getTransacciones() {
		transaccionList = gt.getTransacciones();
		return null;
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

	public String getCuentaOrigen() {
		return cuentaOrigen;
	}

	public void setCuentaOrigen(String cuentaOrigen) {
		this.cuentaOrigen = cuentaOrigen;
	}

	public String getCuentaDestino() {
		return cuentaDestino;
	}

	public void setCuentaDestino(String cuentaDestino) {
		this.cuentaDestino = cuentaDestino;
	}
}
