package vista;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import modelo.Cuenta;
import modelo.Tipo_Cuenta;
import modelo.Usuario;
import negocio.GestionCuentas;

@ManagedBean
@SessionScoped
public class GestionCuentasBean {

	@Inject
	private GestionCuentas gc;
	
	private Cuenta cuenta = new Cuenta();
	private List<Cuenta> cuentas;
	
	private List<Cuenta> busqueda;
	
	private int numeroCuenta;
	
	private String eliminarNumero;
	
	public String guardar() {
		gc.guardar(cuenta);
		//cuentas=gc.getCuentas();
		init();
		return "listar-cuentas";
	}
	
	@PostConstruct
	public void init() {
		cuenta = new Cuenta();
		cuenta.setUsuario(new Usuario());
		cuenta.setTipo_cuenta(new Tipo_Cuenta());
		cuentas=gc.getCuentas();
	}
	
	public void buscar() {
		cuenta=gc.obtenerCuentaNumero(eliminarNumero);
		this.eliminar();
		this.init();
	}
	
	public String eliminar() {
		gc.eliminar(this.cuenta.getCodigo_cuenta());
		cuentas=gc.getCuentas();
		return null;
	}
//	
	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public List<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}

	public int getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(int numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public List<Cuenta> getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(List<Cuenta> busqueda) {
		this.busqueda = busqueda;
	}

	public String getEliminarNumero() {
		return eliminarNumero;
	}

	public void setEliminarNumero(String eliminarNumero) {
		this.eliminarNumero = eliminarNumero;
	}

	
}
