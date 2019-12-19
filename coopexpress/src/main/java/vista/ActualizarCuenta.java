package vista;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import modelo.Cuenta;
import negocio.GestionCuentas;

@ManagedBean
@SessionScoped
public class ActualizarCuenta {
	
	@Inject
	private GestionCuentas gc;
	
	private Cuenta cuenta;

	private String numeroCuenta;
	
	private List<Cuenta> cuentas;
	
	public String buscar() {
		cuentas= gc.listaEliminar(numeroCuenta);
		return null;
	}
	
	@PostConstruct
	public void init() {
		cuenta= new Cuenta();
	}
	
	public String actualizar() {
		gc.actualizar(cuenta);
		return null;
	}
	public String eliminar() {
		gc.eliminar(this.cuenta.getCodigo_cuenta());
		return null;
	}
	
	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public List<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}
//	
//	public void listadoCuentas() {
//		cuentas = gc.getCuentas();
//		//return "crear-cuenta";
//	}
//	

}
