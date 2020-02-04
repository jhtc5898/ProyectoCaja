package vista;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import modelo.Credito;
import modelo.Credito_Detalle;
import modelo.Cuenta;
import negocio.GestionCredito;
import negocio.GestionCreditoDetalle;
import negocio.GestionCuentas;

@ManagedBean
@SessionScoped
public class GestionCreditoBean {

	@Inject
	private GestionCredito gc;
	
	@Inject
	private GestionCreditoDetalle gcd;
	
	@Inject
	private GestionCuentas gcu;
	
	private Credito credito =  new Credito();
	private List<Credito_Detalle> creditoDetalles = new ArrayList<Credito_Detalle>();
	private String numeroCuenta;
	private String mensaje;

	@PostConstruct
	public void init() {
		credito = new Credito();
	}
	
	public String guardarSolicitudCredito() {
		gc.guardar(credito);
		init();
		return "informe-credito";
	}
		
	public String getCreditoDisponible(String numeroCuenta) {
		credito = gc.getCreditoCuentaDisponible(numeroCuenta);
		if(credito == null) {
			setMensaje("No existen creditos registrados en esta cuenta");
		}
		return "informe-credito";
	}
	
	public String solicitar(String numeroCuenta) {
		Cuenta cuenta = gcu.obtenerCuentaNumero(numeroCuenta);
		credito.setCodigo_cuenta(cuenta);
		guardarSolicitudCredito();
		return null;
	}
	
	public void getPagosCredito(String numeroCuenta) {
		creditoDetalles = gcd.getPagos(Integer.parseInt(numeroCuenta));
	}

	public Credito getCredito() {
		return credito;
	}

	public void setCredito(Credito credito) {
		this.credito = credito;
	}

	public List<Credito_Detalle> getCreditoDetalles() {
		return creditoDetalles;
	}

	public void setCreditoDetalles(List<Credito_Detalle> creditoDetalles) {
		this.creditoDetalles = creditoDetalles;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public void vaciarMensaje() {
		setMensaje(" ");
	}

	
} 
