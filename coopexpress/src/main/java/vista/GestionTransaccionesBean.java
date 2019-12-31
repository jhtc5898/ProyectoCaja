package vista;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import modelo.Tipo_Transaccion;
import modelo.Transaccion;
import negocio.GestionTipoTransaccion;
import negocio.GestionTransaccion;

@ManagedBean
@SessionScoped
public class GestionTransaccionesBean {

	@Inject
	private GestionTransaccion gt;
	private GestionTipoTransaccion gtt;
	
	private List<Transaccion> transaccionList = new ArrayList<Transaccion>();
	
	private Transaccion transaccion = new Transaccion();	
	
	@PostConstruct
	public void init() {
		transaccion = new Transaccion();
		long millis=System.currentTimeMillis();  
		java.sql.Date fecha=new java.sql.Date(millis);
		transaccion.setFecha_transaccion(fecha);
	}
	
	public String guardarDeposito() {
		Tipo_Transaccion deposito = gtt.getTipoTransaccionNombre("Deposito");
		transaccion.setTipo_transaccion(deposito);
		gt.guardarTransaccionDeposito(transaccion);
		init();
		return null;
	}
	
	public String guardarRetiro() {
		Date fecha = new Date();
		Tipo_Transaccion deposito = gtt.getTipoTransaccionNombre("Retiro");
		transaccion.setFecha_transaccion(fecha);
		transaccion.setTipo_transaccion(deposito);
		gt.guardarTransaccionRetiro(transaccion);
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
}
